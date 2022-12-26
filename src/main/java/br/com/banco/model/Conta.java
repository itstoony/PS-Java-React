package br.com.banco.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entity representing Account
 */
@Setter
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 9999)
    @Column(name = "agencia", unique = true)
    private Integer agencia;

    @NotNull
    @Size(min = 1111, max = 99999999)
    @Column(name = "numero", unique = true)
    private Integer numero;

    @NotNull
    @Column(name = "nome_responsavel")
    @NotEmpty(message = "nome responsável não deve ser vazio")
    private String nomeResponsavel;

    @Email
    @NotNull
    @NotEmpty
    @Column(name = "email")
    private String email;

    @Column(name = "saldo", columnDefinition = "DECIMAL(20,2) DEFAULT 0.00")
    private Double saldo;

    @Column(name = "data_de_criacao")
    private LocalDateTime dataDeCriacao;

    public Conta(String nomeResponsavel, int agencia, int conta, String email) {
        this.nomeResponsavel = nomeResponsavel;
        this.agencia = agencia;
        this.numero = conta;
        this.saldo = 0.00;
        this.email = email;
        this.dataDeCriacao = LocalDateTime.now();
    }

}
