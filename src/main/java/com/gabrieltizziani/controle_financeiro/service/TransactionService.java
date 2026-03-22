package com.gabrieltizziani.controle_financeiro.service;

import com.gabrieltizziani.controle_financeiro.domain.Account;
import com.gabrieltizziani.controle_financeiro.domain.Category;
import com.gabrieltizziani.controle_financeiro.domain.Transaction;
import com.gabrieltizziani.controle_financeiro.domain.User;
import com.gabrieltizziani.controle_financeiro.domain.enums.StatusAccount;
import com.gabrieltizziani.controle_financeiro.domain.enums.TransactionType;
import com.gabrieltizziani.controle_financeiro.dto.transaction.CreateTransactionRequest;
import com.gabrieltizziani.controle_financeiro.dto.transaction.TransactionResponse;
import com.gabrieltizziani.controle_financeiro.dto.transaction.UpdateTransactionRequest;
import com.gabrieltizziani.controle_financeiro.repository.AccountRepository;
import com.gabrieltizziani.controle_financeiro.repository.CategoryRepository;
import com.gabrieltizziani.controle_financeiro.repository.TransactionRepository;
import com.gabrieltizziani.controle_financeiro.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AccountRepository accountRepository;

    private void applyTransactionEffect(Account account, TransactionType type, BigDecimal amount) {
        if (type == TransactionType.RECEITA) {
            account.setCurrentBalance(account.getCurrentBalance().add(amount));
        } else if (type == TransactionType.DESPESA) {
            account.setCurrentBalance(account.getCurrentBalance().subtract(amount));
        }
    }

    private void reverseTransactionEffect(Account account, TransactionType type, BigDecimal amount) {
        if (type == TransactionType.RECEITA) {
            account.setCurrentBalance(account.getCurrentBalance().subtract(amount));
        } else if (type == TransactionType.DESPESA) {
            account.setCurrentBalance(account.getCurrentBalance().add(amount));
        }
    }

    @Transactional
    public Transaction createTransaction(CreateTransactionRequest createTransactionRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepository.findByIdAndUserId(createTransactionRequest.accountId(), userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Category category = categoryRepository.findByIdAndUserId(createTransactionRequest.categoryId(), userId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (account.getStatusAccount() == StatusAccount.INATIVA) {
            throw new RuntimeException("Inactive account cannot receive transactions");
        }

        Transaction transaction = new Transaction(createTransactionRequest, user, account, category);

        applyTransactionEffect(account, createTransactionRequest.transactionType(), createTransactionRequest.amount());
        accountRepository.save(account);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction updateTransaction(Long id, UpdateTransactionRequest request, Long userId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Account oldAccount = transaction.getAccount();
        TransactionType oldType = transaction.getTransactionType();
        BigDecimal oldAmount = transaction.getAmount();

        Account newAccount = oldAccount;
        if (request.accountId() != null && !request.accountId().equals(oldAccount.getId())) {
            newAccount = accountRepository.findByIdAndUserId(request.accountId(), userId)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
        }

        Category newCategory = transaction.getCategory();
        if (request.categoryId() != null && !request.categoryId().equals(transaction.getCategory().getId())) {
            newCategory = categoryRepository.findByIdAndUserId(request.categoryId(), userId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        TransactionType newType = request.transactionType() != null ? request.transactionType() : oldType;
        BigDecimal newAmount = request.amount() != null ? request.amount() : oldAmount;

        reverseTransactionEffect(oldAccount, oldType, oldAmount);
        applyTransactionEffect(newAccount, newType, newAmount);

        transaction.updateTransaction(request, newAccount, newCategory);

        accountRepository.save(oldAccount);

        if (!oldAccount.getId().equals(newAccount.getId())) {
            accountRepository.save(newAccount);
        }

        return transactionRepository.save(transaction);
    }

    @Transactional
    public void deleteTransaction(Long id, Long userId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Account account = transaction.getAccount();

        reverseTransactionEffect(account, transaction.getTransactionType(), transaction.getAmount());

        accountRepository.save(account);
        transactionRepository.delete(transaction);
    }

    public List<TransactionResponse> findAllTransactions(Long userId) {
        return transactionRepository.findAllByUserIdOrderByTransactionDateDesc(userId)
                .stream()
                .map(TransactionResponse::new)
                .toList();
    }

    public TransactionResponse findTransactionById(Long id, Long userId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        return new TransactionResponse(transaction);
    }


}
