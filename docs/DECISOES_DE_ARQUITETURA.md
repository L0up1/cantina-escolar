# Decisões de Arquitetura

## ADR-001 — Banco de Dados

Foi escolhido o PostgreSQL por ser um banco de dados robusto, gratuito e amplamente utilizado no mercado, além de possuir excelente integração com o Spring Boot.

---

## ADR-002 — Controle de Estoque

A primeira versão do sistema não possuirá controle de estoque.

A cantina realiza reposição mensal dos produtos e atualmente não existe necessidade operacional para controlar entradas e saídas.

Essa funcionalidade poderá ser implementada em versões futuras sem impacto na arquitetura atual.

---

## ADR-003 — Histórico de Preços

O ItemVenda armazenará o preço unitário do produto no momento da venda.

Essa decisão garante que alterações futuras no preço dos produtos não modifiquem o histórico financeiro das vendas já realizadas.

---

## ADR-004 — Disponibilidade do Produto

Produtos não serão excluídos quando deixarem de ser vendidos temporariamente.

Será utilizado um indicador de disponibilidade para impedir novas vendas sem perder o histórico.

---

## ADR-005 — Categorias

Os produtos serão organizados por categorias.

Essa decisão facilitará pesquisas, filtros e a organização da tela de vendas, além de permitir crescimento futuro do catálogo.