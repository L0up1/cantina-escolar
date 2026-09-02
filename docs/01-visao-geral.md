# Sistema de Controle de Cantina Escolar

## Objetivo

O Sistema de Controle de Cantina Escolar tem como objetivo informatizar o processo de registro e controle das vendas realizadas na cantina escolar.

O projeto surgiu para substituir o controle manual feito em caderno, reduzindo erros de anotação, perda de informações e dificuldades no acompanhamento das compras realizadas pelos alunos.

O sistema permite cadastrar alunos e produtos, registrar vendas, controlar pagamentos e pendências e gerar informações financeiras para auxiliar na administração da cantina.

---

## Público-alvo

- Funcionários da cantina
- Administração escolar

---

## Principais funcionalidades

- Cadastro e gerenciamento de alunos
- Cadastro e gerenciamento de categorias
- Cadastro e gerenciamento de produtos
- Registro de vendas
- Registro de vendas vinculadas a alunos
- Pagamentos à vista e parciais
- Pagamentos utilizando diferentes formas de pagamento
- Controle de vendas pendentes
- Quitação de múltiplas vendas pendentes
- Consulta de vendas
- Resumos e relatórios financeiros
- Autenticação de usuários
- Controle de acesso por perfil

---

## Tecnologias

### Backend

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- JWT
- Bean Validation
- Maven

### Banco de dados

- PostgreSQL

### Documentação da API

- Swagger / OpenAPI

### Testes

- JUnit
- Mockito

### Frontend

O frontend será desenvolvido separadamente e consumirá a API REST disponibilizada pelo backend.

---

## Arquitetura

O backend é organizado em camadas, separando as responsabilidades da aplicação:

- Controller — exposição dos endpoints HTTP
- Service — regras de negócio
- Repository — acesso ao banco de dados
- Entity — representação das entidades persistidas
- DTO — entrada e saída de dados da API
- Mapper — conversão entre entidades e DTOs
- Config — configurações de segurança e infraestrutura
- Exception — tratamento centralizado de erros

---

## Objetivos do projeto

- Eliminar o controle manual das vendas
- Agilizar o atendimento da cantina
- Reduzir erros de lançamento
- Manter histórico das vendas
- Controlar pagamentos e pendências
- Facilitar o acompanhamento financeiro
- Gerar relatórios
- Garantir acesso autenticado às funcionalidades do sistema