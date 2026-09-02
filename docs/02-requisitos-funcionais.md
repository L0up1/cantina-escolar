# Requisitos Funcionais

## Alunos

### RF01 — Cadastrar aluno

O sistema deve permitir o cadastro de alunos.

### RF02 — Gerenciar alunos

O sistema deve permitir consultar e atualizar os dados dos alunos.

---

## Categorias e Produtos

### RF03 — Cadastrar categorias

O sistema deve permitir o cadastro de categorias para organização dos produtos.

### RF04 — Gerenciar categorias

O sistema deve permitir consultar, editar e excluir categorias conforme as regras da aplicação.

### RF05 — Cadastrar produtos

O sistema deve permitir cadastrar produtos com seus respectivos dados e valores.

### RF06 — Gerenciar produtos

O sistema deve permitir consultar e atualizar produtos cadastrados.

### RF07 — Controlar disponibilidade

O sistema deve permitir definir se um produto está disponível para venda.

---

## Vendas

### RF08 — Registrar venda

O sistema deve permitir registrar uma venda contendo um ou mais itens.

### RF09 — Vincular venda ao aluno

O sistema deve permitir vincular uma venda a um aluno quando necessário.

### RF10 — Calcular valor da venda

O sistema deve calcular o valor total da venda com base nos produtos, preços e quantidades informadas.

### RF11 — Consultar vendas

O sistema deve permitir consultar vendas registradas.

---

## Pagamentos

### RF12 — Registrar pagamento

O sistema deve permitir registrar pagamentos de uma venda.

### RF13 — Pagamento parcial

O sistema deve permitir pagamentos inferiores ao valor total da venda quando ela estiver vinculada a um aluno.

### RF14 — Pagamento misto

O sistema deve permitir utilizar mais de uma forma de pagamento para quitar uma venda.

### RF15 — Controlar pendências

O sistema deve identificar vendas como pagas ou pendentes conforme os pagamentos realizados.

### RF16 — Quitar vendas pendentes

O sistema deve permitir registrar pagamentos posteriores para vendas pendentes de um aluno.

### RF17 — Distribuir pagamento entre pendências

Quando um aluno possuir múltiplas vendas pendentes, o sistema deve distribuir o pagamento entre elas, priorizando as vendas mais antigas.

---

## Relatórios

### RF18 — Resumo financeiro

O sistema deve fornecer informações consolidadas sobre vendas, valores pagos e valores pendentes.

### RF19 — Relatório por forma de pagamento

O sistema deve permitir consultar os valores recebidos de acordo com as formas de pagamento utilizadas.

---

## Usuários e Segurança

### RF20 — Autenticar usuário

O sistema deve permitir a autenticação de usuários cadastrados.

### RF21 — Gerar token de acesso

Após autenticação válida, o sistema deve fornecer um token JWT para acesso aos recursos protegidos.

### RF22 — Controlar permissões

O sistema deve restringir funcionalidades conforme o perfil do usuário.

Os perfis atualmente utilizados são:

- ADMIN
- OPERADOR