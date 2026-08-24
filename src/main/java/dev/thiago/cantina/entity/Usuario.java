package dev.thiago.cantina.entity;

import dev.thiago.cantina.enums.Cargo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, unique = true, length = 50)
    private String login;
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

}
