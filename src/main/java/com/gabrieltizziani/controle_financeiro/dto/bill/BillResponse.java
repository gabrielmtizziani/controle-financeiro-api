package com.gabrieltizziani.controle_financeiro.dto.bill;

import com.gabrieltizziani.controle_financeiro.domain.Bill;
import com.gabrieltizziani.controle_financeiro.domain.enums.BillStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BillResponse(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate dueDate,
        LocalDate paymentDate,
        BillStatus status,
        String accountName,
        String categoryName
) {
    public BillResponse(Bill bill) {
        this(
                bill.getId(),
                bill.getDescription(),
                bill.getAmount(),
                bill.getDueDate(),
                bill.getPaymentDate(),
                bill.getStatus(),
                bill.getAccount().getNameAccount(),
                bill.getCategory().getNameCategory()
        );
    }
}