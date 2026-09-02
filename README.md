# 🍔 Cantina Escolar

Sistema de gerenciamento de cantina escolar desenvolvido com **Java e Spring Boot**, criado para substituir o controle manual de vendas, pagamentos e pendências de alunos.

O projeto permite registrar vendas, controlar pagamentos parciais e mistos, acompanhar débitos de alunos, gerar informações financeiras e controlar o acesso ao sistema através de autenticação JWT.

> Projeto desenvolvido com foco no estudo e aplicação prática de desenvolvimento backend com Java e Spring Boot.

---

## 📌 Sobre o projeto

O sistema surgiu a partir de uma necessidade real de gerenciamento de uma cantina escolar.

Antes do sistema, as compras realizadas pelos alunos eram registradas manualmente, dificultando o acompanhamento das vendas, pagamentos e valores pendentes.

A aplicação centraliza essas informações e aplica automaticamente as principais regras de negócio da cantina.

---

## 🚀 Funcionalidades

- Cadastro e gerenciamento de alunos
- Cadastro e gerenciamento de categorias
- Cadastro e gerenciamento de produtos
- Controle de disponibilidade dos produtos
- Registro de vendas
- Vendas vinculadas a alunos
- Pagamentos à vista
- Pagamentos parciais
- Pagamentos mistos
- Controle de vendas pendentes
- Quitação posterior de débitos
- Distribuição automática de pagamentos entre vendas pendentes
- Priorização das vendas mais antigas durante a quitação
- Resumo financeiro das vendas
- Relatório por forma de pagamento
- Autenticação com JWT
- Controle de acesso por perfil
- Tratamento centralizado de erros
- Documentação da API com Swagger/OpenAPI

---

## 🛠️ Tecnologias

### Backend

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Bean Validation
- JWT (JJWT)
- Maven

### Banco de dados

- PostgreSQL

### Documentação

- Swagger / OpenAPI

### Testes

- JUnit
- Mockito

---

## 🏗️ Arquitetura

O backend é organizado em camadas:

```text
src/main/java/dev/thiago/cantina
│
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
└── service
```

Cada camada possui uma responsabilidade específica:

- **Controller:** exposição dos endpoints HTTP
- **Service:** regras de negócio
- **Repository:** comunicação com o banco de dados
- **Entity:** entidades persistidas pelo JPA
- **DTO:** objetos utilizados na entrada e saída da API
- **Mapper:** conversão entre entidades e DTOs
- **Config:** segurança e configurações da aplicação
- **Exception:** exceções e tratamento centralizado de erros

---

## 💳 Pagamentos

Uma venda pode possuir múltiplos pagamentos.

Isso permite registrar cenários como:

```text
Venda: R$ 20,00

PIX:      R$ 12,00
Dinheiro: R$  8,00

Total pago: R$ 20,00
Status: PAGO
```

Também é possível registrar pagamentos parciais.

Quando uma venda não é totalmente paga, ela permanece como `PENDENTE` e deve estar vinculada a um aluno.

### Quitação de pendências

Quando um aluno possui várias vendas pendentes, o sistema utiliza o pagamento para quitar primeiro as vendas mais antigas.

Exemplo:

```text
Venda 1: R$ 10,00
Venda 2: R$ 10,00

Pagamento: R$ 15,00

Resultado:

Venda 1 → R$ 10,00 pagos → PAGO
Venda 2 → R$  5,00 pagos → PENDENTE
```

---

## 🔐 Segurança

A API utiliza **Spring Security + JWT**.

Após realizar o login, o usuário recebe um token JWT que deve ser enviado nas requisições aos endpoints protegidos.

Atualmente existem dois perfis:

| Perfil | Descrição |
|---|---|
| `ADMIN` | Acesso às funcionalidades administrativas |
| `OPERADOR` | Acesso às operações permitidas da cantina |

A aplicação utiliza autenticação **stateless**, sem armazenamento de sessão no servidor.

---

## 📚 Documentação da API

Com a aplicação em execução, a documentação dos endpoints pode ser acessada através do Swagger UI.

```text
http://localhost:8080/swagger-ui/index.html
```

O Swagger também está configurado para permitir autenticação utilizando o token JWT.

---

## ⚙️ Configuração

O projeto utiliza variáveis de ambiente para informações sensíveis.

Configure:

```env
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_jwt
```

A configuração da aplicação utiliza essas variáveis:

```properties
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

> Nunca adicione senhas ou chaves JWT diretamente ao repositório.

---

## 🗄️ Banco de dados

Crie um banco PostgreSQL chamado:

```text
cantina
```

A configuração padrão utiliza:

```text
jdbc:postgresql://localhost:5432/cantina
```

Durante o desenvolvimento, o Hibernate está configurado para atualizar o schema automaticamente.

---

## ▶️ Executando o projeto

### Pré-requisitos

Tenha instalado:

- Java 21
- PostgreSQL

Clone o repositório e entre na pasta do projeto.

Configure as variáveis de ambiente necessárias e execute:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

## 🧪 Testes

Os testes automatizados utilizam **JUnit e Mockito**.

Para executar:

### Windows

```powershell
.\mvnw.cmd test
```

### Linux/macOS

```bash
./mvnw test
```

Os testes atuais cobrem principalmente as regras de negócio de vendas e pagamentos, incluindo pagamentos parciais, pagamentos mistos, distribuição entre pendências e cenários inválidos.

---

## 📖 Documentação técnica

Informações mais detalhadas estão disponíveis na pasta [`docs`](./docs):

- [Visão geral](./docs/01-visao-geral.md)
- [Requisitos funcionais](./docs/02-requisitos-funcionais.md)
- [Regras de negócio](./docs/03-regras-de-negocio.md)
- [Decisões de arquitetura](./docs/DECISOES_DE_ARQUITETURA.md)
- [Changelog](./docs/CHANGELOG.md)

---

## 🗺️ Próximas etapas

- Containerização com Docker
- Desenvolvimento/integração do frontend
- Ampliação da cobertura de testes
- Evolução dos relatórios
- Migrações de banco de dados
- Melhorias na implantação e execução do sistema

## 🤖 Uso de Inteligência Artificial

Durante o desenvolvimento deste projeto, ferramentas de Inteligência Artificial foram utilizadas como apoio ao aprendizado, revisão de código, discussão de decisões de arquitetura e resolução de dúvidas técnicas.

A IA foi utilizada como ferramenta de suporte durante o processo de desenvolvimento, enquanto as regras de negócio, decisões técnicas, implementação e validação das funcionalidades foram construídas e compreendidas ao longo do projeto.

---

## 👨‍💻 Autor

Desenvolvido por **Thiago Riguett** como projeto de estudo e aplicação prática de Java, Spring Boot e desenvolvimento backend.