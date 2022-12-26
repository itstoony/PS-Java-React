package br.com.banco.Repository;

import br.com.banco.model.Transferencia;
import br.com.banco.model.enums.Operacao;
import br.com.banco.repository.TransferenciaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class TransferenciaRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando salvar na base uma transferencia com o id informado")
    public void salvaTransferenciaTest() {

        // Cenário
        Transferencia transferencia = transfBuilder();

        // Execução
        Transferencia transfSalva = transferenciaRepository.save(transferencia);

        // Validações
        Assertions.assertThat(transfSalva.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar uma transferencia")
    public void deleteTransferenciaTest() {
        // Cenário
        Transferencia transferencia = transfBuilder();
        transferenciaRepository.save(transferencia);

        Transferencia transEncontrada = testEntityManager.find(Transferencia.class, transferencia.getId());

        // Execução
        transferenciaRepository.delete(transEncontrada);
        Transferencia transfDeletada = testEntityManager.find(Transferencia.class, transferencia.getId());

        // Validações
        Assertions.assertThat(transfDeletada).isNull();
    }

    @Test
    @DisplayName("Deve obter uma transferencia por id")
    public void findByIdTest() {
        // Cenário
        Transferencia transferencia = transfBuilder();
        transferenciaRepository.save(transferencia);

        // Execução
        Optional<Transferencia> transfEncontrada = transferenciaRepository.findById(1L);

        // Validações
        Assertions.assertThat(transfEncontrada.isPresent());
    }

    private static Transferencia transfBuilder() {
        return Transferencia.builder()
                .id(1L)
                .dataTransferencia(null)
                .valor(10.0)
                .conta(null)
                .tipo(Operacao.TRANSFERENCIA_ENTRADA)
                .nomeOperadorTransacao("Nome Operação")
                .saldoAtual(10.0)
                .build();
    }

}
