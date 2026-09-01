package dev.thiago.cantina.service;

import dev.thiago.cantina.dto.item_venda.ItemVendaRequestDTO;
import dev.thiago.cantina.dto.pagamento.PagamentoRequestDTO;
import dev.thiago.cantina.dto.venda.PagamentoVendaRequestDTO;
import dev.thiago.cantina.dto.venda.VendaRequestDTO;
import dev.thiago.cantina.dto.venda.VendaResponseDTO;
import dev.thiago.cantina.entity.Aluno;
import dev.thiago.cantina.entity.Produto;
import dev.thiago.cantina.entity.Venda;
import dev.thiago.cantina.enums.FormaPagamento;
import dev.thiago.cantina.enums.StatusPagamento;
import dev.thiago.cantina.exception.VendaInvalidaException;
import dev.thiago.cantina.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {
    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private ItemVendaRepository itemVendaRepository;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private VendaService vendaService;

    @Test
    void deveSalvarVendaPaga() {

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Fini");
        produto.setValorVenda(new BigDecimal("5.00"));

        ItemVendaRequestDTO itemDTO =
                new ItemVendaRequestDTO(1L, 2);

        PagamentoRequestDTO pagamentoDTO =
                new PagamentoRequestDTO(
                        FormaPagamento.PIX,
                        new BigDecimal("10.00")
                );

        VendaRequestDTO vendaDTO =
                new VendaRequestDTO(
                        List.of(itemDTO),
                        null,
                        List.of(pagamentoDTO)
                );
        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        when(vendaRepository.save(any(Venda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        VendaResponseDTO resultado = vendaService.salvar(vendaDTO);

        assertEquals(new BigDecimal("10.00"), resultado.valorTotal());
        assertEquals(new BigDecimal("10.00"), resultado.valorPago());
        assertEquals(new BigDecimal("0.00"), resultado.valorPendente());
        assertEquals(StatusPagamento.PAGO, resultado.statusPagamento());
    }

    @Test
    void deveSalvarVendaParcialmentePaga() {

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Fini");
        produto.setValorVenda(new BigDecimal("5.00"));

        Aluno aluno = new Aluno();
        aluno.setId(1L);

        ItemVendaRequestDTO itemDTO =
                new ItemVendaRequestDTO(1L, 2);

        PagamentoRequestDTO pagamentoDTO =
                new PagamentoRequestDTO(
                        FormaPagamento.PIX,
                        new BigDecimal("4.00")
                );

        VendaRequestDTO vendaDTO =
                new VendaRequestDTO(
                        List.of(itemDTO),
                        1L,
                        List.of(pagamentoDTO)
                );

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        when(alunoRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(vendaRepository.save(any(Venda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        VendaResponseDTO resultado = vendaService.salvar(vendaDTO);

        assertEquals(new BigDecimal("10.00"), resultado.valorTotal());
        assertEquals(new BigDecimal("4.00"), resultado.valorPago());
        assertEquals(new BigDecimal("6.00"), resultado.valorPendente());
        assertEquals(StatusPagamento.PENDENTE, resultado.statusPagamento());
    }

    @Test
    void deveLancarErroQuandoVendaParcialNaoPossuirAluno() {

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Fini");
        produto.setValorVenda(new BigDecimal("5.00"));

        ItemVendaRequestDTO itemDTO =
                new ItemVendaRequestDTO(1L, 2);

        PagamentoRequestDTO pagamentoDTO =
                new PagamentoRequestDTO(
                        FormaPagamento.PIX,
                        new BigDecimal("4.00")
                );

        VendaRequestDTO vendaDTO =
                new VendaRequestDTO(
                        List.of(itemDTO),
                        null, // não tem aluno
                        List.of(pagamentoDTO)
                );

    }

    @Test
    void deveLancarErroVendaPagoAMais() {

        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Misto");
        produto.setValorVenda(new BigDecimal("3.50"));

        Aluno aluno = new Aluno();
        aluno.setId(1L);

        ItemVendaRequestDTO itemDTO =
                new ItemVendaRequestDTO(1L, 2);

        PagamentoRequestDTO pagamentoDTO =
                new PagamentoRequestDTO(
                        FormaPagamento.PIX,
                        new BigDecimal("9.00")
                );

        VendaRequestDTO vendaDTO =
                new VendaRequestDTO(
                        List.of(itemDTO),
                        1L,
                        List.of(pagamentoDTO)
                );

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        when(alunoRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        VendaInvalidaException exception = assertThrows(
                VendaInvalidaException.class,
                () -> vendaService.salvar(vendaDTO)
        );

        verify(vendaRepository, never())
                .save(any(Venda.class));
    }

    @Test
    void deveSalvarVendaComPagamentoMisto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Fini");
        produto.setValorVenda(new BigDecimal("5.00"));


        ItemVendaRequestDTO itemDTO = new ItemVendaRequestDTO(1L, 2);

        PagamentoRequestDTO pagamentoPix = new PagamentoRequestDTO(
                FormaPagamento.PIX,
                new BigDecimal("6.00")
        );

        PagamentoRequestDTO pagamentoDinheiro = new PagamentoRequestDTO(
                FormaPagamento.DINHEIRO,
                new BigDecimal("4.00")
        );

        VendaRequestDTO vendaDTO =
                new VendaRequestDTO(
                        List.of(itemDTO),
                        null,
                        List.of(pagamentoPix, pagamentoDinheiro)
                );

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        when(vendaRepository.save(any(Venda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        VendaResponseDTO resultado =
                vendaService.salvar(vendaDTO);

        assertEquals(
                new BigDecimal("10.00"),
                resultado.valorTotal()
        );

        assertEquals(
                new BigDecimal("10.00"),
                resultado.valorPago()
        );

        assertEquals(
                new BigDecimal("0.00"),
                resultado.valorPendente()
        );

        assertEquals(
                StatusPagamento.PAGO,
                resultado.statusPagamento()
        );

        assertEquals(
                2,
                resultado.pagamentos().size()
        );

        assertEquals(
                FormaPagamento.PIX,
                resultado.pagamentos().get(0).formaPagamento()
        );

        assertEquals(
                new BigDecimal("6.00"),
                resultado.pagamentos().get(0).valor()
        );

        assertEquals(
                FormaPagamento.DINHEIRO,
                resultado.pagamentos().get(1).formaPagamento()
        );

        assertEquals(
                new BigDecimal("4.00"),
                resultado.pagamentos().get(1).valor()
        );
    }

    @Test
    void deveDistribuirPagamentoEntreVendasPendentes() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setAluno(aluno);
        venda1.setDataHora(LocalDateTime.of(2026, 8, 1, 10, 0));
        venda1.setValorTotal(new BigDecimal("5.00"));
        venda1.setStatusPagamento(StatusPagamento.PENDENTE);
        venda1.setItens(new ArrayList<>());
        venda1.setPagamentos(new ArrayList<>());

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setAluno(aluno);
        venda2.setDataHora(LocalDateTime.of(2026, 8, 10, 10, 0));
        venda2.setValorTotal(new BigDecimal("10.00"));
        venda2.setStatusPagamento(StatusPagamento.PENDENTE);
        venda2.setItens(new ArrayList<>());
        venda2.setPagamentos(new ArrayList<>());

        PagamentoRequestDTO pagamento = new PagamentoRequestDTO(
                FormaPagamento.PIX,
                new BigDecimal("8.00")
        );

        PagamentoVendaRequestDTO dto =
                new PagamentoVendaRequestDTO(List.of(pagamento));

        when(alunoRepository.findById(1L))
                .thenReturn(Optional.of(aluno));
        when(vendaRepository.findByAlunoIdAndStatusPagamento(
                1L,
                StatusPagamento.PENDENTE
        )).thenReturn(new ArrayList<>(List.of(venda2, venda1)));

        List<VendaResponseDTO> resultado =
                vendaService.atualizarPagamento(1L, dto);

        assertEquals(2, resultado.size());

        assertEquals(
                new BigDecimal("5.00"),
                resultado.get(0).valorPago()
        );

        assertEquals(
                new BigDecimal("0.00"),
                resultado.get(0).valorPendente()
        );

        assertEquals(
                StatusPagamento.PAGO,
                resultado.get(0).statusPagamento()
        );

        assertEquals(
                new BigDecimal("3.00"),
                resultado.get(1).valorPago()
        );

        assertEquals(
                new BigDecimal("7.00"),
                resultado.get(1).valorPendente()
        );

        assertEquals(
                StatusPagamento.PENDENTE,
                resultado.get(1).statusPagamento()
        );

        verify(vendaRepository).saveAll(anyList());
    }

    @Test
    void deveLancarErroPagamentoPendenteAMais(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setAluno(aluno);
        venda1.setDataHora(LocalDateTime.of(2026, 8, 10, 10, 0));
        venda1.setValorTotal(new BigDecimal("10.00"));
        venda1.setStatusPagamento(StatusPagamento.PENDENTE);
        venda1.setItens(new ArrayList<>());
        venda1.setPagamentos(new ArrayList<>());

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setAluno(aluno);
        venda2.setDataHora(LocalDateTime.of(2026, 8, 10, 10, 0));
        venda2.setValorTotal(new BigDecimal("5.00"));
        venda2.setStatusPagamento(StatusPagamento.PENDENTE);
        venda2.setItens(new ArrayList<>());
        venda2.setPagamentos(new ArrayList<>());

        PagamentoRequestDTO pagamento = new PagamentoRequestDTO(
                FormaPagamento.PIX,
                new BigDecimal("20.00")
        );

        PagamentoVendaRequestDTO dto =
                new PagamentoVendaRequestDTO(List.of(pagamento));

        when(alunoRepository.findById(1L))
                .thenReturn(Optional.of(aluno));
        when(vendaRepository.findByAlunoIdAndStatusPagamento(
                1L,
                StatusPagamento.PENDENTE
        )).thenReturn(new ArrayList<>(List.of(venda2, venda1)));

        VendaInvalidaException exception = assertThrows(
                VendaInvalidaException.class,
                () -> vendaService.atualizarPagamento(1L, dto)
        );

        assertEquals(
                "O valor informado é maior que o total pendente do aluno.",
                exception.getMessage()
        );

        verify(vendaRepository, never())
                .saveAll(anyList());
    }

    @Test
    void deveLancarErroQuandoAlunoNaoPossuirVendasPendentes() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        PagamentoRequestDTO pagamento =
                new PagamentoRequestDTO(
                        FormaPagamento.PIX,
                        new BigDecimal("10.00")
                );

        PagamentoVendaRequestDTO dto =
                new PagamentoVendaRequestDTO(
                        List.of(pagamento)
                );

        when(alunoRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(vendaRepository.findByAlunoIdAndStatusPagamento(
                1L,
                StatusPagamento.PENDENTE
        )).thenReturn(List.of());

        VendaInvalidaException exception = assertThrows(
                VendaInvalidaException.class,
                () -> vendaService.atualizarPagamento(1L, dto)
        );

        assertEquals(
                "O aluno não possui vendas pendentes.",
                exception.getMessage()
        );

        verify(vendaRepository, never())
                .saveAll(anyList());
    }

    @Test
    void deveSalvarPagamentoMistoVendasPendentes() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Venda venda1 = new Venda();
        venda1.setId(1L);
        venda1.setAluno(aluno);
        venda1.setDataHora(LocalDateTime.of(2026, 8, 10, 10, 0));
        venda1.setValorTotal(new BigDecimal("10.00"));
        venda1.setStatusPagamento(StatusPagamento.PENDENTE);
        venda1.setItens(new ArrayList<>());
        venda1.setPagamentos(new ArrayList<>());

        Venda venda2 = new Venda();
        venda2.setId(2L);
        venda2.setAluno(aluno);
        venda2.setDataHora(LocalDateTime.of(2026, 8, 20, 10, 0));
        venda2.setValorTotal(new BigDecimal("10.00"));
        venda2.setStatusPagamento(StatusPagamento.PENDENTE);
        venda2.setItens(new ArrayList<>());
        venda2.setPagamentos(new ArrayList<>());

        PagamentoRequestDTO pagamento1 =
                new PagamentoRequestDTO(
                        FormaPagamento.PIX,
                        new BigDecimal("12.00")
                );
        PagamentoRequestDTO pagamento2 = new PagamentoRequestDTO(
                FormaPagamento.DINHEIRO,
                new BigDecimal("8.00")
        );

        PagamentoVendaRequestDTO dto =
                new PagamentoVendaRequestDTO(
                        List.of(pagamento1, pagamento2)
                );

        when(alunoRepository.findById(1L))
                .thenReturn(Optional.of(aluno));

        when(vendaRepository.findByAlunoIdAndStatusPagamento(
                1L,
                StatusPagamento.PENDENTE
        )).thenReturn(
                new ArrayList<>(List.of(venda1, venda2))
        );

        List<VendaResponseDTO> resultado =
                vendaService.atualizarPagamento(1L, dto);

        assertEquals(2, resultado.size());

        assertEquals(
                StatusPagamento.PAGO,
                resultado.get(0).statusPagamento()
        );

        assertEquals(
                new BigDecimal("10.00"),
                resultado.get(0).valorPago()
        );

        assertEquals(
                new BigDecimal("0.00"),
                resultado.get(0).valorPendente()
        );

        assertEquals(StatusPagamento.PAGO,
                resultado.get(1).statusPagamento()
        );

        assertEquals(new BigDecimal("10.00"),
                resultado.get(1).valorPago()
        );

        assertEquals(new BigDecimal("0.00"),
                resultado.get(1).valorPendente()
        );

        assertEquals(
                1,
                resultado.get(0).pagamentos().size()
        );

        assertEquals(
                FormaPagamento.PIX,
                resultado.get(0).pagamentos().get(0).formaPagamento()
        );

        assertEquals(
                new BigDecimal("10.00"),
                resultado.get(0).pagamentos().get(0).valor()
        );


        assertEquals(
                2,
                resultado.get(1).pagamentos().size()
        );

        assertEquals(
                FormaPagamento.PIX,
                resultado.get(1).pagamentos().get(0).formaPagamento()
        );

        assertEquals(
                new BigDecimal("2.00"),
                resultado.get(1).pagamentos().get(0).valor()
        );

        assertEquals(
                FormaPagamento.DINHEIRO,
                resultado.get(1).pagamentos().get(1).formaPagamento()
        );

        assertEquals(
                new BigDecimal("8.00"),
                resultado.get(1).pagamentos().get(1).valor()
        );

        verify(vendaRepository).saveAll(anyList());
    }
}
