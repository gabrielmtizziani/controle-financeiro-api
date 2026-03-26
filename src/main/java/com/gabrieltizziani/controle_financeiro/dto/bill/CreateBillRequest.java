package com.gabrieltizziani.controle_financeiro.dto.bill;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateBillRequest(
        @NotNull(message = "Description is required")
        String description,

        @NotNull
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Due date is required")
        LocalDate dueDate,

        @NotNull(message = "Account id is required")
        Long accountId,

        @NotNull(message = "Category id is required")
        Long categoryId
) {
}
