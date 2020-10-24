package br.com.builders.infra.mongo.repository;

import br.com.builders.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
    @Query("{\"cpf\": ?0, \"nome\": /?1/}")
    Page<Cliente> findByCpfAndNome(String cpf, String nome, Pageable pageable);
}