# Regras de Negócio

## RN01 — Itens da venda

Uma venda deve possuir pelo menos um item.

---

## RN02 — Quantidade

A quantidade informada para um item da venda deve ser maior que zero.

---

## RN03 — Preço da venda

O valor do item deve ser calculado utilizando o preço atual do produto no momento da venda.

O preço utilizado deve permanecer registrado no ItemVenda para preservar o histórico financeiro.

---

## RN04 — Status de pagamento

Uma venda pode possuir os seguintes estados de pagamento:

- PAGO
- PENDENTE

O status deve ser determinado de acordo com o valor total da venda e os pagamentos registrados.

---

## RN05 — Venda totalmente paga

Quando o valor total dos pagamentos for igual ao valor total da venda, a venda deve ser registrada com status PAGO.

---

## RN06 — Venda parcialmente paga

Quando o valor pago for inferior ao valor total da venda, a venda deve permanecer com status PENDENTE.

Uma venda parcialmente paga deve estar vinculada a um aluno.

---

## RN07 — Venda sem pagamento integral

Uma venda que possuir saldo pendente deve estar associada a um aluno para permitir a cobrança posterior.

---

## RN08 — Pagamento superior ao valor da venda

O sistema não deve permitir que o valor dos pagamentos seja superior ao valor total da venda.

---

## RN09 — Pagamentos mistos

Uma venda pode possuir múltiplos pagamentos utilizando diferentes formas de pagamento.

Cada pagamento deve manter registrada sua própria forma de pagamento e valor.

---

## RN10 — Pagamento de vendas pendentes

O sistema deve permitir registrar posteriormente pagamentos para vendas que estejam com status PENDENTE.

---

## RN11 — Ordem de quitação

Quando um aluno possuir múltiplas vendas pendentes, os pagamentos devem ser aplicados primeiro às vendas mais antigas.

---

## RN12 — Distribuição do pagamento

Quando o valor de um pagamento ultrapassar o saldo de uma venda pendente, apenas o valor necessário para quitá-la deve ser aplicado.

O restante deve ser utilizado na próxima venda pendente do aluno.

---

## RN13 — Quitação da venda

Quando o saldo pendente de uma venda chegar a zero, seu status deve ser alterado para PAGO.

---

## RN14 — Limite do pagamento de pendências

O sistema não deve aceitar um pagamento cujo valor seja superior à soma de todas as vendas pendentes do aluno.

---

## RN15 — Formas de pagamento

O sistema deve registrar a forma de pagamento utilizada em cada pagamento realizado.

---

## RN16 — Segurança

As funcionalidades protegidas do sistema somente podem ser acessadas por usuários autenticados.

Determinados recursos administrativos devem ser acessíveis somente por usuários com perfil ADMIN.