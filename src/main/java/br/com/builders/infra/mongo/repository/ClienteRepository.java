package br.com.builders.infra.mongo.repository;

import br.com.builders.domain.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> { }