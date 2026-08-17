package dev.thiago.cantina.service;

import dev.thiago.cantina.dto.item_venda.ItemVendaRequestDTO;
import dev.thiago.cantina.dto.venda.VendaRequestDTO;
import dev.thiago.cantina.dto.venda.VendaResponseDTO;
import dev.thiago.cantina.entity.Aluno;
import dev.thiago.cantina.entity.ItemVenda;
import dev.thiago.cantina.entity.Produto;
import dev.thiago.cantina.entity.Venda;
import dev.thiago.cantina.enums.StatusPagamento;
import dev.thiago.cantina.exception.*;
import dev.thiago.cantina.mapper.VendaMapper;
import dev.thiago.cantina.repository.AlunoRepository;
import dev.thiago.cantina.repository.ProdutoRepository;
import dev.thiago.cantina.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final AlunoRepository alunoRepository;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository, AlunoRepository alunoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.alunoRepository = alunoRepository;
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
        venda.setStatusPagamento(dto.statusPagamento());
        venda.setFormaPagamento(dto.formaPagamento());
        venda.setItens(new ArrayList<>());

        if (StatusPagamento.PENDENTE.equals(dto.statusPagamento()) && dto.alunoId() == null) {
            throw new VendaInvalidaException("Uma venda pendente deve ter um aluno.");
        }

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

        Venda vendaSalva = vendaRepository.save(venda);
        return VendaMapper.toDTO(vendaSalva);
    }

    @Transactional(readOnly = true)
    public List<VendaResponseDTO> listar() {
        List<Venda> vendas = vendaRepository.findAll();
        return vendas.stream().map(VendaMapper::toDTO).toList();
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
    public List<VendaResponseDTO> listarTodosEntrePeriodo(LocalDate inicio, LocalDate fim) {
        if (fim.isBefore(inicio)) {
            throw new PeriodoInvalidoException(
                    "A data final não pode ser anterior à data inicial."
            );
        }
        List<Venda> vendasFiltradas = vendaRepository.findByDataHoraBetween(inicio.atStartOfDay(),fim.atTime(LocalTime.MAX));
        return vendasFiltradas.stream().map(VendaMapper::toDTO).toList();

    }
 }
