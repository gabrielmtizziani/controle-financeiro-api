package com.gabrieltizziani.controle_financeiro.dto.transaction;


import com.gabrieltizziani.controle_financeiro.domain.Transaction;
import com.gabrieltizziani.controle_financeiro.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        LocalDate transactionDate,
        Long accountId,
        String accountName,
        Long categoryId,
        String categoryName,
        TransactionType transactionType,
        BigDecimal amount,
        String description
) {
    public TransactionResponse(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getTransactionDate(),
                transaction.getAccount().getId(),
                transaction.getAccount().getNameAccount(),
                transaction.getCategory().getId(),
                transaction.getCategory().getNameCategory(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription()
        );
    }
}