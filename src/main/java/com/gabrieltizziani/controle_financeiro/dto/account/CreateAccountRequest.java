package com.gabrieltizziani.controle_financeiro.dto.account;

import com.gabrieltizziani.controle_financeiro.domain.enums.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "The account name is required")
        String nameAccount,
        @NotNull(message = "The account type is required")
        AccountType accountType,
        @NotNull
        @DecimalMin(value = "0.0", message = ("The current balance cannot be negative"))
        BigDecimal currentBalance
) {
}
