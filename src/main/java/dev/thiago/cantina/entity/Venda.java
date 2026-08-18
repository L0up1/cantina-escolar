package dev.thiago.cantina.entity;

import dev.thiago.cantina.enums.FormaPagamento;
import dev.thiago.cantina.enums.StatusPagamento;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vendas")
@Getter
@Setter
@NoArgsConstructor
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHora;
    private BigDecimal valorTotal;
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento statusPagamento;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private FormaPagamento formaPagamento;
    @OneToMany(
            mappedBy = "venda",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ItemVenda> itens;
}
