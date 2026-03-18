package com.gabrieltizziani.controle_financeiro.dto.account;


import com.gabrieltizziani.controle_financeiro.domain.Account;
import com.gabrieltizziani.controle_financeiro.domain.enums.AccountType;
import com.gabrieltizziani.controle_financeiro.domain.enums.StatusAccount;

import java.math.BigDecimal;

public record AccountResponse(Long id,
                              String nameAccount,
                              AccountType accountType,
                              BigDecimal currentBalance,
                              StatusAccount statusAccount
) {
    public AccountResponse(Account account){
        this(
                account.getId(),
                account.getNameAccount(),
                account.getAccountType(),
                account.getCurrentBalance(),
                account.getStatusAccount()
        );
}}
