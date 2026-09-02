# Changelog

## Sprint 0 — Inicialização

- Projeto Spring Boot criado.
- Repositório Git configurado.
- Estrutura inicial do projeto definida.

---

## Sprint 1 — Planejamento

- Documentação inicial criada.
- Requisitos funcionais definidos.
- Regras de negócio iniciais definidas.
- Decisões iniciais de arquitetura documentadas.

---

## Sprint 2 — Cadastros

- Implementado cadastro de categorias.
- Implementado gerenciamento de categorias.
- Implementado cadastro e gerenciamento de produtos.
- Implementado cadastro e gerenciamento de alunos.
- Criadas camadas de Controller, Service, Repository, DTO e Mapper.

---

## Sprint 3 — Vendas

- Implementado registro de vendas.
- Implementado ItemVenda.
- Implementado cálculo do valor das vendas.
- Implementado vínculo entre vendas e alunos.
- Implementada consulta de vendas.

---

## Sprint 4 — Pagamentos

- Criada entidade Pagamento.
- Implementado relacionamento entre Venda e Pagamento.
- Implementados pagamentos parciais.
- Implementados pagamentos mistos.
- Implementado controle de vendas pendentes.
- Implementada quitação posterior de vendas pendentes.
- Implementada distribuição de pagamentos entre múltiplas vendas.
- Definida prioridade de quitação das vendas mais antigas.
- Implementadas validações para impedir pagamentos acima do saldo pendente.

---

## Sprint 5 — Relatórios

- Implementado resumo financeiro das vendas.
- Implementado cálculo de valores pagos.
- Implementado cálculo de valores pendentes.
- Implementado relatório por forma de pagamento.

---

## Sprint 6 — Segurança

- Implementada autenticação com Spring Security.
- Implementada autenticação baseada em JWT.
- Implementados perfis ADMIN e OPERADOR.
- Configuradas permissões de acesso aos endpoints.
- Configuradas respostas personalizadas para erros 401 e 403.
- Integrada autenticação JWT à documentação Swagger/OpenAPI.
- Credenciais e chave JWT movidas para variáveis de ambiente.

---

## Sprint 7 — Testes

- Criados testes unitários para VendaService.
- Testadas vendas pagas.
- Testadas vendas parcialmente pagas.
- Testados pagamentos mistos.
- Testada distribuição de pagamentos entre vendas pendentes.
- Testadas validações de pagamentos inválidos.
- Testados cenários de alunos sem vendas pendentes.