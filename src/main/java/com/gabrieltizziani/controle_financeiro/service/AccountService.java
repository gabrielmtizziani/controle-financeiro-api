package com.gabrieltizziani.controle_financeiro.service;

import com.gabrieltizziani.controle_financeiro.domain.Account;
import com.gabrieltizziani.controle_financeiro.domain.User;
import com.gabrieltizziani.controle_financeiro.domain.enums.StatusAccount;
import com.gabrieltizziani.controle_financeiro.dto.account.AccountResponse;
import com.gabrieltizziani.controle_financeiro.dto.account.CreateAccountRequest;
import com.gabrieltizziani.controle_financeiro.dto.account.UpdateAccountRequest;
import com.gabrieltizziani.controle_financeiro.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account createAccount(CreateAccountRequest createAccountRequest, User user) {
        if (accountRepository.existsByNameAccountAndUserId(createAccountRequest.nameAccount(), user.getId())) {
            throw new RuntimeException("You already have an account with this name");
        }

        Account account = new Account(createAccountRequest, user);
        return accountRepository.save(account);
    }

    public List<AccountResponse> findAllAccounts(Long userId) {
        return accountRepository.findAllByUserId(userId)
                .stream()
                .map(AccountResponse::new)
                .toList();
    }

    @Transactional
    public Account updateAccount(Long id, UpdateAccountRequest updateAccountRequest, Long userId) {
        var account = accountRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (updateAccountRequest.nameAccount() != null &&
                accountRepository.existsByNameAccountAndUserIdAndIdNot(
                        updateAccountRequest.nameAccount(),
                        userId,
                        id
                )) {
            throw new RuntimeException("You already have an account with this name");
        }

        account.updateAccount(updateAccountRequest);
        return accountRepository.save(account);
    }

    @Transactional
    public void inactivateAccount(Long id, Long userId) {
        var account = accountRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getStatusAccount()== StatusAccount.INATIVA){
            throw new RuntimeException("Account inactivated");
        }

        account.inactivateAccount();
        accountRepository.save(account);
    }
}