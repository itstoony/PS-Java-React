package br.com.banco.Service;

import br.com.banco.model.Transferencia;
import br.com.banco.model.enums.Operacao;
import br.com.banco.repository.TransferenciaRepository;
import br.com.banco.service.TransferenciaService;
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
public class TransferenciaServiceTest {

    private TransferenciaService transferenciaService;

    @MockBean
    private TransferenciaRepository transferenciaRepository;

    @BeforeEach
    public void setup() {
        this.transferenciaService = new TransferenciaService(transferenciaRepository);
    }

    @Test
    @DisplayName("Deve salvar uma transferencia")
    public void saveTransferenciaTest() {
        // Cenário
        Transferencia transferencia = createTransferencia();
        Transferencia transfRetornada = Transferencia.builder()
                .id(1L)
                .dataTransferencia(null)
                .valor(10.0)
                .conta(null)
                .tipo(Operacao.TRANSFERENCIA_ENTRADA)
                .nomeOperadorTransacao("Nome Operação")
                .saldoAtual(10.0)
                .build();

        Mockito.when(transferenciaRepository.save(transferencia)).thenReturn(transfRetornada);

        // Execução
        Transferencia trasnfSalva = transferenciaService.inserir(transferencia);

        // Validações
        Assertions.assertThat(trasnfSalva.getId()).isNotNull();
        Assertions.assertThat(trasnfSalva.getDataTransferencia()).isEqualTo(transferencia.getDataTransferencia());
        Assertions.assertThat(trasnfSalva.getValor()).isEqualTo(transferencia.getValor());
        Assertions.assertThat(trasnfSalva.getConta()).isEqualTo(transferencia.getConta());
        Assertions.assertThat(trasnfSalva.getTipo()).isEqualTo(transferencia.getTipo());
        Assertions.assertThat(trasnfSalva.getNomeOperadorTransacao()).isEqualTo(transferencia.getNomeOperadorTransacao());
        Assertions.assertThat(trasnfSalva.getSaldoAtual()).isEqualTo(transferencia.getSaldoAtual());
    }



    private Transferencia createTransferencia() {
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
