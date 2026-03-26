package com.gabrieltizziani.controle_financeiro.dto.bill;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateBillRequest(
        String description,
        BigDecimal amount,
        LocalDate dueDate,
        Long accountId,
        Long categoryId
) {}