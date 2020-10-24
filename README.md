# Spring Boot "Microservice" Builders Project
REST APIs implemented using Spring Boot Maven Project

## Requerimentos

1. Java - 11.x
2. Docker
3. docker-compose

## Como rodar o Docker

* Construa o projeto rodando o comando: `./mvnw clean package`.
* Para um build + run, rode esse comando:`./mvnw spring-boot:run`

Alternativamente, você pode rodar o projeto empacotado no JAR -
`bash
java -jar target/builders-0.0.1-SNAPSHOT.jar
`

## Como rodar o Docker

* Para rodar o docker, crie a imagem com esse script: `docker build -f Dockerfile -t builders .  ` 
* Após a imagem ser criada rode o docker compose: `docker-compose -f docker-compose.yml up --build -d `


## Acesse a documentação via Open API
* Rode no Browser a URL a seguir: `http://localhost:8080/swagger-ui.html`

## Json Postman
* Existe um arquivo atualizado com todos os testes via Postman:
`Builder.postman_collection.json

	