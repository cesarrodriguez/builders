package br.com.builders.infra.mongo.config;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = {"br.com.builders.repository"})
public class MongodbConfig { }