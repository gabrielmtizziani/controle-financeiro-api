package com.gabrieltizziani.controle_financeiro.domain;

import com.gabrieltizziani.controle_financeiro.domain.enums.TransactionType;
import com.gabrieltizziani.controle_financeiro.dto.account.UpdateTransactionRequest;
import com.gabrieltizziani.controle_financeiro.dto.transaction.CreateTransactionRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "transaction")
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Transaction(CreateTransactionRequest request,
                       User user,
                       Account account,
                       Category category) {

        this.description = request.description().trim();
        this.amount = request.amount();
        this.transactionType = request.transactionType();
        this.transactionDate = request.transactionDate();
        this.createdAt = LocalDateTime.now();

        this.user = user;
        this.account = account;
        this.category = category;
    }

    public void updateTransaction(UpdateTransactionRequest updateTransactionRequest){

    }
}
