package br.com.builders.domain.service;

import br.com.builders.domain.model.Cliente;
import br.com.builders.infra.mongo.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public Optional<Cliente> getClienteById(final String id){
        return repository.findById(id);
    }

    public Cliente upsertCliente(final Cliente cliente){
        return repository.save(cliente);
    }

    public void deleteCliente(final Cliente cliente){
        repository.delete(cliente);
    }
}
