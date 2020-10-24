package br.com.builders.controller;

import br.com.builders.controller.resources.ClienteResource;
import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
                    .notFound()
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
        var clientedb = service.getClienteById(id);

        if(!clientedb.isPresent()
                || (clientedb.get().getNome() == null && clientedb.get().getDataNascimento() == null)){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .build();
        }
        var cliente = clientedb.get();
        cliente.setId(id);
        cliente.setNome(clienteResource.getNome());
        cliente.setDataNascimento(clienteResource.getDataNascimento());

        var clienteAtualizado = service.upsertCliente(cliente);
        log.info("Atualizou o cliente com id: " + clienteAtualizado.getId() );
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(clienteAtualizado);
    }

    @DeleteMapping(path = "{id}")
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

    @GetMapping()
    public ResponseEntity<Page<Cliente>> getClienteQuery(@RequestParam("cpf") String cpf,
                                                         @RequestParam("nome") String nome,
                                                         @RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
                                                         @RequestParam(value = "tamanho", required = false, defaultValue = "10") int tamanho) {
        log.info("Buscando o cliente com cpf: " + cpf + " e nome " + nome + " ...");
        var cliente = service.getClienteByCpfAndNome(cpf, nome, pagina, tamanho);
        if (cliente == null || cliente.getNumberOfElements() == 0) {
            log.info("Não encontrou o cliente com cpf: " + cpf + " e nome " + nome + " ...");
            return ResponseEntity
                    .notFound()
                    .build();
        }
        cliente.getContent().forEach(c -> c.addIdade());
        log.info("Encntrou o cliente com cpf: " + cpf + " e nome " + nome + " ...");
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(cliente);
    }
}
