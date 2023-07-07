# Gerenciamento de conta bancária

API desenvolvido em Kotlin para gerenciamento de conta bancária utilizando arquitetura limpa.

## Tecnologias

- Kotlin;
- Spring Boot;
- PostgreSQL.

## Requisitos para rodar o projeto:

- Docker;
- Java 1.8;
- Maven.

## Como rodar o projeto

- para subir o banco de dados:

```sh
docker-compose up
```

- para compilar o projeto e rodar os testes unitários:

```sh
mvn clean install
```

- para subir a aplicação:

```sh
java -jar bank-app/target/bank-app.jar
```

- para subir a aplicação em modo debug:

```sh
java -jar -Dspring.profiles.active=debug bank-app/target/bank-app.jar
```

## Documentação da API

Depois de subir a aplicação, acesse esse [link](http://localhost:8080/swagger-ui.html) para a documentação.

## Como rodar os testes integrados

Depois de subir a aplicação, siga o passo a passo do README do projeto abaixo:

- [bank-manager-e2e](https://github.com/brunoroger/bank-manager-e2e).
