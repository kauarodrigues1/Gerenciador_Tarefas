# Task Management API

API REST para gerenciamento de tarefas com autenticação de usuários utilizando JWT.

O projeto foi desenvolvido com Java e Spring Boot, com foco em desenvolvimento de APIs REST, autenticação, integração com banco de dados, testes, containerização e deploy em ambiente AWS.

## 🚀 Tecnologias

- Java 17
- Spring Boot 4.1.1
- Spring Security
- JWT
- PostgreSQL
- JPA / Hibernate
- Docker
- Docker Compose
- AWS EC2
- Swagger / OpenAPI
- JUnit
- Mockito
- Maven

## ✨ Funcionalidades

- Cadastro de usuários
- Login de usuários
- Criptografia de senhas com BCrypt
- Autenticação utilizando JWT
- Proteção de endpoints
- CRUD de tarefas
- Tarefas vinculadas ao usuário autenticado
- Controle de acesso às tarefas de cada usuário
- Validação dos dados das requisições
- Tratamento global de exceções
- Testes unitários com JUnit e Mockito
- Documentação da API com Swagger
- Aplicação containerizada com Docker
- PostgreSQL executando através do Docker Compose
- Utilização de variáveis de ambiente para configurações sensíveis
- Deploy da aplicação na AWS EC2

## 🏗️ Arquitetura

A aplicação utiliza uma arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
