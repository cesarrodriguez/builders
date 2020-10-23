package br.com.builders.controller;

import br.com.builders.controller.resources.ClienteResource;
import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/clientes")
public class ClienteController {

    private final ClienteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> get(@PathVariable String id) {
        log.info("Buscando o cliente com id: " + id + "...");
        var cliente = service.getClienteById(id);
        if(cliente == null || !cliente.isPresent()){
            log.info("Não encontrou o cliente com id: " + id);
            return ResponseEntity
                    .noContent()
                    .build();
        }
        log.info("Encontrou o cliente com id: " + id );
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(cliente.get());

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> insert(@RequestBody Cliente cliente) {
        log.info("Criando o cliente : " + cliente.getNome() + "...");
        var clientedb = service.upsertCliente(cliente);
        log.info("Cadastrou o cliente com id: " + clientedb );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientedb);

    }

    @PutMapping(path = "{id}",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> update(@RequestBody Cliente cliente, @PathVariable String id) {
        log.info("Atualizando o cliente : " + id + "...");
        cliente.setId(id);
        var clientedb = service.upsertCliente(cliente);
        log.info("Atualizou o cliente com id: " + clientedb.getId() );
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientedb);
    }

    @PatchMapping(path = "{id}",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> update(@RequestBody ClienteResource clienteResource, @PathVariable String id) {
        log.info("Atualizando o cliente : " + id + "...");
        var cliente = new Cliente();
        cliente.setId(id);
        if(clienteResource.getNome() != null && !clienteResource.getNome().equals("")) {
            cliente.setNome(clienteResource.getNome());
        }
        if(clienteResource.getDataNasciemnto() != null) {
            cliente.setDataNascimento(clienteResource.getDataNasciemnto());
        }
        if(cliente.getNome() == null && cliente.getDataNascimento() == null){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .build();
        }
        var clientedb = service.upsertCliente(cliente);
        log.info("Atualizou o cliente com id: " + clientedb.getId() );
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientedb);
    }

    @DeleteMapping(path = "{id}",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deletando o cliente : " + id + "...");
        var cliente = new Cliente();
        cliente.setId(id);
        service.deleteCliente(cliente);
        log.info("Deletou o cliente com id: " + id );
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
