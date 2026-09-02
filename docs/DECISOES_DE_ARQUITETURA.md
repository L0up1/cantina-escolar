# Decisões de Arquitetura

## ADR-001 — Banco de Dados

Foi escolhido o PostgreSQL por ser um banco de dados relacional robusto, gratuito e amplamente utilizado no mercado, além de possuir boa integração com o Spring Boot.

---

## ADR-002 — Controle de Estoque

A primeira versão do sistema não possuirá controle de estoque.

A disponibilidade dos produtos será controlada sem gerenciamento de quantidade armazenada.

O controle de estoque poderá ser implementado futuramente caso exista necessidade operacional.

---

## ADR-003 — Histórico de Preços

O ItemVenda armazena o preço unitário do produto utilizado no momento da venda.

Essa decisão garante que alterações futuras no preço dos produtos não modifiquem o histórico financeiro das vendas já realizadas.

---

## ADR-004 — Disponibilidade do Produto

Produtos que deixarem temporariamente de ser vendidos não precisam ser removidos do sistema.

Será utilizado um indicador de disponibilidade para impedir novas vendas sem comprometer o histórico.

---

## ADR-005 — Categorias

Os produtos são organizados por categorias.

Essa organização facilita a manutenção do catálogo e poderá ser utilizada posteriormente para pesquisas e filtros no frontend.

---

## ADR-006 — Separação entre Venda e Pagamento

Venda e Pagamento são entidades distintas.

Uma venda pode possuir múltiplos pagamentos, permitindo:

- pagamentos parciais;
- pagamentos posteriores;
- pagamentos mistos;
- registro individual da forma de pagamento;
- acompanhamento do saldo pendente.

Essa modelagem evita vincular uma única forma de pagamento diretamente à venda.

---

## ADR-007 — Preservação do histórico de pagamentos

Cada pagamento registra seu valor, forma de pagamento e data/hora.

Os pagamentos permanecem associados à venda correspondente, preservando o histórico financeiro da operação.

---

## ADR-008 — Distribuição de pagamentos pendentes

Quando um aluno possui múltiplas vendas pendentes, os pagamentos são distribuídos das vendas mais antigas para as mais recentes.

Essa decisão torna o processo de quitação previsível e evita que pendências antigas permaneçam abertas enquanto vendas mais recentes são quitadas primeiro.

---

## ADR-009 — Autenticação com JWT

A API utiliza autenticação baseada em JWT.

Após a autenticação, o cliente envia o token nas requisições aos recursos protegidos.

A aplicação utiliza sessões stateless, evitando a necessidade de manter sessões de usuário no servidor.

---

## ADR-010 — Controle de acesso por perfil

O sistema utiliza perfis de acesso para restringir determinadas operações.

Os perfis atuais são:

- ADMIN
- OPERADOR

Operações administrativas possuem restrições específicas de autorização.

---

## ADR-011 — DTOs

A API utiliza DTOs para entrada e saída de dados.

As entidades JPA não são utilizadas diretamente como contrato HTTP, reduzindo o acoplamento entre persistência e API.

---

## ADR-012 — Separação entre backend e frontend

O backend disponibiliza uma API HTTP responsável pelas regras de negócio, segurança e persistência.

O frontend é uma aplicação separada e consome essa API.

Essa decisão permite evolução independente das duas camadas e evita acesso direto do frontend ao banco de dados.

---

## ADR-013 — Tratamento centralizado de exceções

As exceções da aplicação são tratadas de forma centralizada.

O objetivo é fornecer respostas HTTP consistentes e evitar duplicação do tratamento de erros nos controllers.

---

## ADR-014 — Testes das regras de venda

As principais regras de negócio relacionadas a vendas e pagamentos possuem testes unitários utilizando JUnit e Mockito.

Os repositories são simulados durante esses testes para que o comportamento do VendaService seja validado isoladamente.