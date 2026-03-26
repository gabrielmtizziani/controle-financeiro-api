package com.gabrieltizziani.controle_financeiro.controller;

import com.gabrieltizziani.controle_financeiro.domain.Transaction;
import com.gabrieltizziani.controle_financeiro.domain.User;
import com.gabrieltizziani.controle_financeiro.dto.transaction.CreateTransactionRequest;
import com.gabrieltizziani.controle_financeiro.dto.transaction.TransactionResponse;
import com.gabrieltizziani.controle_financeiro.dto.transaction.UpdateTransactionRequest;
import com.gabrieltizziani.controle_financeiro.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> findAllTransactionsByAccount(
            @PathVariable Long accountId,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(transactionService.findAllTransactionsByAccount(user.getId(), accountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findTransactionById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(transactionService.findTransactionById(id, user.getId()));
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody @Valid CreateTransactionRequest createTransactionRequest, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Transaction transaction = transactionService.createTransaction(createTransactionRequest, user.getId());
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionRequest updateTransactionRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Transaction transaction = transactionService.updateTransaction(id, updateTransactionRequest, user.getId());
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        transactionService.deleteTransaction(id, user.getId());
        return ResponseEntity.noContent().build();
    }



}
