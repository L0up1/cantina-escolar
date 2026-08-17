package dev.thiago.cantina.entity;

import dev.thiago.cantina.enums.Turma;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turma turma;
    @OneToMany(mappedBy = "aluno")
    private List<Venda> vendas = new ArrayList<>();
}
