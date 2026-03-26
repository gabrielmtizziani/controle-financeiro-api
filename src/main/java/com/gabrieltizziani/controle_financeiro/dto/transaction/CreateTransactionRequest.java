package com.gabrieltizziani.controle_financeiro.dto.transaction;

import com.gabrieltizziani.controle_financeiro.domain.Account;
import com.gabrieltizziani.controle_financeiro.domain.Category;
import com.gabrieltizziani.controle_financeiro.domain.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotNull(message = "Transaction date is required")
        LocalDate transactionDate,
        @NotNull(message = "Account id is required")
        Long accountId,
        @NotNull(message = "Category id is required")
        Long categoryId,
        @NotNull(message = "Transaction type is required")
        TransactionType transactionType,
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,
        @Size(max = 255, message = "Description must be less than 255 characters")
        @NotNull(message = "Description is required")
        String description
) {
}
