# Assembléia API
Projeto backend utilizando __Spring Boot__, para a gestão de assembléias de cooperados.

## Instruções
A API já está UP na [AWS](http://ec2-100-26-104-207.compute-1.amazonaws.com:8080/actuator/health).  
Para testar a API utilizar a [Collection do Postman](https://www.getpostman.com/collections/bd048c6fb40da480aaea).  

## Features
- Cadastro de Cooperados
- Cadastro de Pautas
- Votação de Pautas

## Tecnologias
- Spring
- DB PostgreSQL
- Liquibase
- Testes Unitários (JUnit, Mockito)
- Apache Kafka  
- Pipeline com Github, CircleCI, SonarCloud

## Pipeline
- CircleCI : https://app.circleci.com/pipelines/github/dboegershausen/assembleia
- SonarCloud : https://sonarcloud.io/summary/overall?id=dboegershausen_assembleia
- AWS : http://ec2-100-26-104-207.compute-1.amazonaws.com:8080/actuator/health

## Rodar Localmente
- docker build -t assembleia .
- docker-compose up -d



