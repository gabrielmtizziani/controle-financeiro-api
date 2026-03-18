package com.gabrieltizziani.controle_financeiro.domain;

import com.gabrieltizziani.controle_financeiro.domain.enums.AccountType;
import com.gabrieltizziani.controle_financeiro.domain.enums.StatusAccount;
import com.gabrieltizziani.controle_financeiro.dto.account.CreateAccountRequest;
import com.gabrieltizziani.controle_financeiro.dto.account.UpdateAccountRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "account")
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_account")
    private String nameAccount;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "current_balance")
    private BigDecimal currentBalance;

    @Enumerated(EnumType.STRING)
    @Column (name = "status_account")
    private StatusAccount statusAccount = StatusAccount.ATIVA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Account(CreateAccountRequest createAccountRequest, User user) {
        this.nameAccount = createAccountRequest.nameAccount();
        this.accountType = createAccountRequest.accountType();
        this.currentBalance = createAccountRequest.currentBalance();
        this.user = user;
    }

    public void updateAccount(UpdateAccountRequest updateAccountRequest){
        if (updateAccountRequest.nameAccount() != null){
            this.nameAccount = updateAccountRequest.nameAccount();
        }
        if (updateAccountRequest.accountType() != null){
            this.accountType = updateAccountRequest.accountType();
        }
        if (updateAccountRequest.currentBalance() != null){
            this.currentBalance = updateAccountRequest.currentBalance();
        }
        if (updateAccountRequest.statusAccount() != null){
            this.statusAccount = updateAccountRequest.statusAccount();
        }
    }
}
