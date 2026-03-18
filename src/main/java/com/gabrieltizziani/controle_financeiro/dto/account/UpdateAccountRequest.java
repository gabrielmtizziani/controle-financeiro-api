package com.gabrieltizziani.controle_financeiro.dto.account;

import com.gabrieltizziani.controle_financeiro.domain.enums.AccountType;
import com.gabrieltizziani.controle_financeiro.domain.enums.StatusAccount;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateAccountRequest(
        @NotNull
        Long id,
        String nameAccount,
        AccountType accountType,
        @DecimalMin(value = "0.0")
        BigDecimal currentBalance,
        StatusAccount statusAccount
) {
}
