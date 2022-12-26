package br.com.banco.model;

import br.com.banco.model.enums.Operacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity representing Transactions
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transferencia")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_transferencia")
    private LocalDateTime dataTransferencia;

    @NotNull
    @Column(name = "valor", nullable = false, columnDefinition = "DECIMAL(20,2)")
    private Double valor;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Operacao tipo;

    @Column(name = "nome_operador_transacao")
    private String nomeOperadorTransacao;

    @Column(name = "saldoAtual")
    private Double saldoAtual;

    @ManyToOne
    private Conta conta;

}
