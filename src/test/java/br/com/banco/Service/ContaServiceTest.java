package br.com.banco.Service;

import br.com.banco.model.Conta;
import br.com.banco.repository.ContaRepository;
import br.com.banco.service.ContaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ContaServiceTest {

    private ContaService contaService;

    @MockBean
    private ContaRepository contaRepository;

    @BeforeEach
    public void setup() {
        this.contaService = new ContaService(contaRepository);
    }

    @Test
    @DisplayName("Deve salvar uma conta")
    public void saveContaTest() {
        // Cenário
        Conta conta = createConta();
        Conta contaRetornada = Conta.builder()
                .id(1L)
                .saldo(0.0)
                .email("email@gmail.com")
                .numero(1234567)
                .agencia(1234)
                .nomeResponsavel("Nome")
                .dataDeCriacao(null)
                .build();

        Mockito.when(contaRepository.save(conta)).thenReturn(contaRetornada);

        // Execução
        Conta contaSalva = contaService.inserir(conta);

        // Validações
        Assertions.assertThat(contaSalva.getId()).isNotNull();
        Assertions.assertThat(contaSalva.getSaldo()).isEqualTo(conta.getSaldo());
        Assertions.assertThat(contaSalva.getEmail()).isEqualTo(conta.getEmail());
        Assertions.assertThat(contaSalva.getNumero()).isEqualTo(conta.getNumero());
        Assertions.assertThat(contaSalva.getAgencia()).isEqualTo(conta.getAgencia());
        Assertions.assertThat(contaSalva.getNomeResponsavel()).isEqualTo(conta.getNomeResponsavel());
    }

    private Conta createConta() {
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
