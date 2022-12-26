package br.com.banco.Repository;

import br.com.banco.model.Conta;
import br.com.banco.repository.ContaRepository;
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
public class ContaRepositoryTest {


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ContaRepository contaRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando salvar na base uma conta com o id informado")
    public void salvaContaTest() {

        // Cenário
        Conta conta = contaBuilder();

        // Execução
        Conta contaSalva = contaRepository.save(conta);

        // Validações
        Assertions.assertThat(contaSalva.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar uma conta")
    public void deleteContaTest() {
        // Cenário
        Conta conta = contaBuilder();
        contaRepository.save(conta);

        // Execução
        contaRepository.delete(conta);
        Conta contaDeletada = testEntityManager.find(Conta.class, conta.getId());

        // Validações
        Assertions.assertThat(contaDeletada).isNull();
    }

    @Test
    @DisplayName("Deve obter uma conta por id")
    public void findByIdTest() {
        // Cenário
        Conta conta = contaBuilder();
        contaRepository.save(conta);

        // Execução
        Optional<Conta> contaEncontrada = contaRepository.findById(1L);

        // Validações
        Assertions.assertThat(contaEncontrada.isPresent());
    }

    private static Conta contaBuilder() {
        return Conta.builder()
                .id(1L)
                .saldo(0.0)
                .email("email@gmail.com")
                .numero(1234567)
                .agencia(1234)
                .nomeResponsavel("Nome")
                .dataDeCriacao(null)
                .build();
    }


}
