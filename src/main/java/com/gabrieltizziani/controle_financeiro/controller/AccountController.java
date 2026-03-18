package com.gabrieltizziani.controle_financeiro.controller;

import com.gabrieltizziani.controle_financeiro.domain.Account;
import com.gabrieltizziani.controle_financeiro.domain.User;
import com.gabrieltizziani.controle_financeiro.dto.account.AccountResponse;
import com.gabrieltizziani.controle_financeiro.dto.account.CreateAccountRequest;
import com.gabrieltizziani.controle_financeiro.dto.account.UpdateAccountRequest;
import com.gabrieltizziani.controle_financeiro.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody @Valid CreateAccountRequest createAccountRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Account account = accountService.createAccount(createAccountRequest, user);
        return ResponseEntity.ok(new AccountResponse(account));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(accountService.findAllAccounts(user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAccountRequest updateAccountRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        var accountUpdate = accountService.updateAccount(id, updateAccountRequest, user.getId());
        return ResponseEntity.ok(new AccountResponse(accountUpdate));
    }

    @PatchMapping
    public ResponseEntity<Void> inactivateAccount(
            @PathVariable Long id, Authentication authentication
    ){
        var user = (User) authentication.getPrincipal();
        accountService.inactivateAccount(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}