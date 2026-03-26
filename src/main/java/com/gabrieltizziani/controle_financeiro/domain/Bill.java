package com.gabrieltizziani.controle_financeiro.domain;

import com.gabrieltizziani.controle_financeiro.domain.enums.BillStatus;
import com.gabrieltizziani.controle_financeiro.dto.bill.CreateBillRequest;
import com.gabrieltizziani.controle_financeiro.dto.bill.UpdateBillRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "bill")
@Table(name = "bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    private LocalDate dueDate;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private BillStatus status = BillStatus.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Bill(CreateBillRequest request, User user, Account account, Category category) {
        this.description = request.description();
        this.amount = request.amount();
        this.dueDate = request.dueDate();

        this.status = BillStatus.PENDENTE;

        this.user = user;
        this.account = account;
        this.category = category;
    }

    public void updateBill(UpdateBillRequest request, Account account, Category category) {

        if (request.description() != null) {
            this.description = request.description().trim();
        }

        if (request.amount() != null) {
            this.amount = request.amount();
        }

        if (request.dueDate() != null) {
            this.dueDate = request.dueDate();
        }

        if (account != null) {
            this.account = account;
        }

        if (category != null) {
            this.category = category;
        }
    }
}
