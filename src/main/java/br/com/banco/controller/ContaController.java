package br.com.banco.controller;

import br.com.banco.model.Conta;
import br.com.banco.model.Transferencia;
import br.com.banco.model.dto.ContaDTO;
import br.com.banco.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/conta")
@CrossOrigin(origins = "*")
public class ContaController {

    @Autowired
    private ContaService contaService;

    /**
     * receives a DTO by Application/JSON, converts to an account and inserts at database
     *
     * @param dto ContaDTO
     */
    @PostMapping(path = "/cadastrar")
    public ResponseEntity<Void> cadastrar(@RequestBody ContaDTO dto) {

        Conta conta = contaService.fromDto(dto);

        Conta contaSalva = contaService.inserir(conta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(contaSalva.getId()).toUri();

        return ResponseEntity.ok().build();
    }

    /**
     * @param id    account id
     * @param valor value
     */
    @PutMapping(path = "/depositar/{id}/{valor}")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @PathVariable Double valor) {

        Conta conta = contaService.findById(id);
        contaService.depositar(conta, valor);

        return ResponseEntity.ok().build();
    }

    /**
     * @param id    account
     * @param valor value
     */
    @PutMapping(path = "/sacar/{id}/{valor}")
    public ResponseEntity<Void> sacar(@PathVariable Long id, @PathVariable Double valor) {

        Conta conta = contaService.findById(id);
        contaService.sacar(conta, valor);

        return ResponseEntity.ok().build();
    }

    /**
     * @param idOrigem  id from account sender
     * @param valor     value to transfer
     * @param idDestino id from account receiver
     */
    @PutMapping(path = "/transferir/{idOrigem}/{valor}/{idDestino}")
    public ResponseEntity<Void> transferir(@PathVariable Long idOrigem,
                                           @PathVariable Double valor,
                                           @PathVariable Long idDestino) {

        Conta origem = contaService.findById(idOrigem);
        Conta destino = contaService.findById(idDestino);

        contaService.transferir(origem, destino, valor);

        return ResponseEntity.ok().build();
    }

    /**
     * @param id         account id
     * @param dataInicio start date period
     * @param dataFim    end date period
     * @param nome       transactioners name
     * @return page of transactions
     */
    @GetMapping(path = "/transacoes/{id}")
    public ResponseEntity<Page<Transferencia>> getAllTransactions(@PathVariable Long id,
                                                                  @RequestParam(required = false, defaultValue = "1900-01-01 00:00") String dataInicio,
                                                                  @RequestParam(required = false, defaultValue = "2999-12-29 00:00") String dataFim,
                                                                  @RequestParam(required = false) String nome,
                                                                  @PageableDefault(size = 5, sort = "dataTransferencia", direction = Sort.Direction.DESC) Pageable pageable) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime inicio = LocalDateTime.parse(dataInicio, formatter);
        LocalDateTime fim = LocalDateTime.parse(dataFim, formatter);

        if (nome == null) {
            Page<Transferencia> page = contaService.findByPeriodo(id, inicio, fim, pageable);
            return ResponseEntity.ok(page);
        }

        Page<Transferencia> page = contaService.findByOperadorAndPeriod(id, inicio, fim, nome, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

}
