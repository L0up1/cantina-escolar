package dev.thiago.cantina.service;

import dev.thiago.cantina.dto.item_venda.ItemVendaRequestDTO;
import dev.thiago.cantina.dto.pagamento.PagamentoRequestDTO;
import dev.thiago.cantina.dto.produto.ProdutoMaisVendidoResponseDTO;
import dev.thiago.cantina.dto.venda.*;
import dev.thiago.cantina.entity.*;
import dev.thiago.cantina.enums.FormaPagamento;
import dev.thiago.cantina.enums.StatusPagamento;
import dev.thiago.cantina.exception.*;
import dev.thiago.cantina.mapper.VendaMapper;
import dev.thiago.cantina.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final AlunoRepository alunoRepository;
    private final ItemVendaRepository itemVendaRepository;
    private final PagamentoRepository pagamentoRepository;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository, AlunoRepository alunoRepository, ItemVendaRepository itemVendaRepository, PagamentoRepository pagamentoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.alunoRepository = alunoRepository;
        this.itemVendaRepository = itemVendaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional
    public VendaResponseDTO salvar(VendaRequestDTO dto) {


        Aluno aluno = null;

        if (dto.alunoId() != null) {
            aluno = alunoRepository.findById(dto.alunoId())
                    .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID '" + dto.alunoId() + "' não encontrado."));
        }

        Venda venda = new Venda();

        venda.setDataHora(LocalDateTime.now());
        venda.setAluno(aluno);
        venda.setItens(new ArrayList<>());
        venda.setPagamentos(new ArrayList<>());

        for (ItemVendaRequestDTO itemDTO:dto.itens()){
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto com ID '" + itemDTO.produtoId() +"' não encontrado."));

            ItemVenda itemVenda = new ItemVenda();

            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemDTO.quantidade());
            itemVenda.setValorUnitario(produto.getValorVenda());

            BigDecimal valorTotal = produto.getValorVenda()
                    .multiply(BigDecimal.valueOf(itemDTO.quantidade()));

            itemVenda.setValorTotal(valorTotal);
            itemVenda.setVenda(venda);

            venda.getItens().add(itemVenda);
        }

        BigDecimal valorTotalVenda = venda.getItens()
                .stream()
                .map(ItemVenda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        venda.setValorTotal(valorTotalVenda);

        BigDecimal totalPago = BigDecimal.ZERO;

        if (dto.pagamentos() != null) {

            for (PagamentoRequestDTO pagamentoDTO : dto.pagamentos()) {

                Pagamento pagamento = new Pagamento();

                pagamento.setValor(pagamentoDTO.valor());
                pagamento.setFormaPagamento(pagamentoDTO.formaPagamento());
                pagamento.setDataHora(LocalDateTime.now());
                pagamento.setVenda(venda);

                venda.getPagamentos().add(pagamento);

                totalPago = totalPago.add(pagamentoDTO.valor());
            }
        }

        if (totalPago.compareTo(valorTotalVenda) > 0) {
            throw new VendaInvalidaException(
                    "O valor pago não pode ser maior que o valor total da venda."
            );
        }

        if (totalPago.compareTo(valorTotalVenda) < 0 && aluno == null) {
            throw new VendaInvalidaException(
                    "Uma venda com valor pendente deve possuir um aluno."
            );
        }

        if (totalPago.compareTo(valorTotalVenda) == 0) {
            venda.setStatusPagamento(StatusPagamento.PAGO);
        } else {
            venda.setStatusPagamento(StatusPagamento.PENDENTE);
        }

        Venda vendaSalva = vendaRepository.save(venda);
        return VendaMapper.toDTO(vendaSalva);
    }

    @Transactional(readOnly = true)
    public Page<VendaResponseDTO> listarTodos(int page, int size) {

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by("dataHora").descending()
        );

        Page<Venda> vendas = vendaRepository.findAll(pageRequest);

        return vendas.map(VendaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public VendaResponseDTO buscarPorId(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Venda com ID '" + id + "' não encontrada"));
        return VendaMapper.toDTO(venda);
    }

    @Transactional
    public void deletar(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new VendaNaoEncontradaException("Venda com ID '" + id + "' não encontrada"));
        vendaRepository.delete(venda);
    }

    @Transactional(readOnly = true)
    public Page<VendaResponseDTO> listarTodosEntrePeriodo(LocalDate inicio, LocalDate fim, int page, int size) {
        if (fim.isBefore(inicio)) {
            throw new PeriodoInvalidoException(
                    "A data final não pode ser anterior à data inicial."
            );
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("dataHora").descending());
        Page<Venda> vendasFiltradas = vendaRepository.findByDataHoraBetween(inicio.atStartOfDay(),fim.atTime(LocalTime.MAX), pageRequest);
        return vendasFiltradas.map(VendaMapper::toDTO);

    }

    @Transactional(readOnly = true)
    public List<VendaResponseDTO> listarVendasPendentesPorId(Long id){
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID '" + id + "' não encontrado."));
        List<Venda> vendasPendentes = vendaRepository.findByAlunoIdAndStatusPagamento(id, StatusPagamento.PENDENTE);
        return vendasPendentes.stream().map(VendaMapper::toDTO).toList();
    }

    @Transactional
    public List<VendaResponseDTO> atualizarPagamento(Long id, PagamentoVendaRequestDTO dto) {
        alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com ID '" + id + "' não encontrado."));
        List<Venda> vendasPendentes = vendaRepository.findByAlunoIdAndStatusPagamento(id, StatusPagamento.PENDENTE);
        if (vendasPendentes.isEmpty()){
            throw new VendaInvalidaException("O aluno não possui vendas pendentes.");
        }

        vendasPendentes.sort(
                Comparator.comparing(Venda::getDataHora)
        );

        BigDecimal totalPendente = vendasPendentes.stream()
                .map(this::calcularSaldoPendente)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorInformado = dto.pagamentos()
                .stream()
                .map(PagamentoRequestDTO::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (valorInformado.compareTo(totalPendente) > 0) {
            throw new VendaInvalidaException(
                    "O valor informado é maior que o total pendente do aluno."
            );
        }
        for (PagamentoRequestDTO pagamentoDTO : dto.pagamentos()) {

            BigDecimal restantePagamento = pagamentoDTO.valor();

            for (Venda venda : vendasPendentes) {

                if (restantePagamento.compareTo(BigDecimal.ZERO) <= 0) {
                    break;
                }

                BigDecimal saldoVenda = calcularSaldoPendente(venda);

                if (saldoVenda.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }

                BigDecimal valorAplicado =
                        restantePagamento.min(saldoVenda);

                Pagamento pagamento = new Pagamento();

                pagamento.setValor(valorAplicado);
                pagamento.setFormaPagamento(
                        pagamentoDTO.formaPagamento()
                );
                pagamento.setDataHora(LocalDateTime.now());
                pagamento.setVenda(venda);

                venda.getPagamentos().add(pagamento);

                restantePagamento =
                        restantePagamento.subtract(valorAplicado);

                BigDecimal novoSaldo = calcularSaldoPendente(venda);

                if (novoSaldo.compareTo(BigDecimal.ZERO) == 0) {
                    venda.setStatusPagamento(StatusPagamento.PAGO);
                }
            }
        }

        vendaRepository.saveAll(vendasPendentes);

        return vendasPendentes.stream()
                .map(VendaMapper::toDTO)
                .toList();
    }

    private BigDecimal calcularSaldoPendente(Venda venda) {

        BigDecimal totalPago = venda.getPagamentos()
                .stream()
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return venda.getValorTotal().subtract(totalPago);
    }

    @Transactional(readOnly = true)
    public VendaResumoResponseDTO buscarResusmo(LocalDate inicio, LocalDate fim) {
         LocalDateTime dataInicio = inicio.atStartOfDay();
    LocalDateTime dataFim = fim.plusDays(1).atStartOfDay();

    List<Venda> vendas = vendaRepository.findByDataHoraBetween(
            dataInicio,
            dataFim
    );

    BigDecimal valorTotal = vendas.stream()
            .map(Venda::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal valorPago = vendas.stream()
            .flatMap(venda -> venda.getPagamentos().stream())
            .map(Pagamento::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal valorPendente = valorTotal.subtract(valorPago);

    return new VendaResumoResponseDTO(
            (long) vendas.size(),
            valorTotal,
            valorPago,
            valorPendente
    );
    }

    @Transactional(readOnly = true)
    public VendaPorPagamentoResponseDTO resumoPorPagamento(LocalDate inicio, LocalDate fim){
        LocalDateTime dataInicio = inicio.atStartOfDay();
        LocalDateTime dataFim = fim.plusDays(1).atStartOfDay();

        List<Venda> vendas = vendaRepository.findByDataHoraBetween(
                dataInicio,
                dataFim
        );

        List<Pagamento> pagamentos =
                pagamentoRepository.findByDataHoraBetween(
                        dataInicio,
                        dataFim
                );

        BigDecimal valorTotal = vendas.stream()
                .map(Venda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorPix = pagamentos.stream()
                .filter(p -> p.getFormaPagamento() == FormaPagamento.PIX)
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorDinheiro = pagamentos.stream()
                .filter(p -> p.getFormaPagamento() == FormaPagamento.DINHEIRO)
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorCartao = pagamentos.stream()
                .filter(p -> p.getFormaPagamento() == FormaPagamento.CARTAO)
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorPagoDasVendas = vendas.stream()
                .flatMap(venda -> venda.getPagamentos().stream())
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorPendente =
                valorTotal.subtract(valorPagoDasVendas);

        return new VendaPorPagamentoResponseDTO(
                (long) vendas.size(),
                valorTotal,
                valorPix,
                valorDinheiro,
                valorCartao,
                valorPendente
        );
    }

    @Transactional(readOnly = true)
    public List<ProdutoMaisVendidoResponseDTO> produtoMaisVendido(LocalDate inicio, LocalDate fim) {
        return itemVendaRepository.produtoMaisVendido(inicio.atStartOfDay(), fim.plusDays(1).atStartOfDay());
    }
 }
