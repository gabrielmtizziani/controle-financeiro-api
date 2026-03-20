package com.gabrieltizziani.controle_financeiro.dto.transaction;

import com.gabrieltizziani.controle_financeiro.domain.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateTransactionRequest(

        LocalDate transactionDate,
        Long accountId,
        Long categoryId,
        TransactionType transactionType,
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,
        @Size(max = 255, message = "Description must be less than 255 characters")
        String description
) {
}
