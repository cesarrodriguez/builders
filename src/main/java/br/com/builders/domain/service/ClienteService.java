package br.com.builders.domain.service;

import br.com.builders.domain.model.Cliente;
import br.com.builders.infra.mongo.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public Page<Cliente> getClienteByCpfAndNome(final String cpf, final String nome, final Integer page, final Integer tamanho){
        return repository.findByCpfAndNome(cpf, nome, PageRequest.of(page, tamanho, Sort.Direction.ASC, "nome"));
    }

    public Cliente upsertCliente(final Cliente cliente){
        return repository.save(cliente);
    }

    public void deleteCliente(final Cliente cliente){
        repository.delete(cliente);
    }
}
