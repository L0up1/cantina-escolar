package dev.thiago.cantina.dto.pagamento;

import dev.thiago.cantina.enums.FormaPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PagamentoRequestDTO(@NotNull(message = "A forma de pagamento é obrigatória.")
                                  FormaPagamento formaPagamento,

                                  @NotNull(message = "O valor do pagamento é obrigatório.")
                                  @DecimalMin(
                                          value = "0.01",
                                          message = "O valor do pagamento deve ser maior que zero."
                                  )
                                  BigDecimal valor) {
}
