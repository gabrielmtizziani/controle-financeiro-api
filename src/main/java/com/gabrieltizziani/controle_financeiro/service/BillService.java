package com.gabrieltizziani.controle_financeiro.service;

import com.gabrieltizziani.controle_financeiro.domain.*;
import com.gabrieltizziani.controle_financeiro.domain.enums.BillStatus;
import com.gabrieltizziani.controle_financeiro.domain.enums.TransactionType;
import com.gabrieltizziani.controle_financeiro.dto.bill.BillResponse;
import com.gabrieltizziani.controle_financeiro.dto.bill.CreateBillRequest;
import com.gabrieltizziani.controle_financeiro.dto.bill.UpdateBillRequest;
import com.gabrieltizziani.controle_financeiro.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @Transactional
    public Bill createBill(CreateBillRequest createBillRequest, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepository.findByIdAndUserId(createBillRequest.accountId(), userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Category category = categoryRepository.findByIdAndUserId(createBillRequest.categoryId(), userId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Bill bill = new Bill(createBillRequest,user, account, category);

        return billRepository.save(bill);
    }

    private void updateBillStatusIfOverdue(Bill bill) {
        if (bill.getStatus() == BillStatus.PENDENTE &&
                bill.getDueDate().isBefore(LocalDate.now())) {
            bill.setStatus(BillStatus.ATRASADO);
        }
    }

    @Transactional
    public List<BillResponse> findAllBills(Long userId) {
        List<Bill> bills = billRepository.findAllByUserIdOrderByDueDateAsc(userId);

        bills.forEach(this::updateBillStatusIfOverdue);
        billRepository.saveAll(bills);

        return bills.stream()
                .map(BillResponse::new)
                .toList();
    }

    @Transactional
    public BillResponse findBillById(Long id, Long userId) {
        Bill bill = billRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        updateBillStatusIfOverdue(bill);
        billRepository.save(bill);

        return new BillResponse(bill);
    }

    @Transactional
    public Bill updateBill(Long id, UpdateBillRequest updateBillRequest, Long userId) {
        Bill bill = billRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        updateBillStatusIfOverdue(bill);

        if (bill.getStatus() == BillStatus.PAGO) {
            throw new RuntimeException("Paid bill cannot be updated");
        }

        if (bill.getStatus() == BillStatus.CANCELADO) {
            throw new RuntimeException("Canceled bill cannot be updated");
        }

        Account newAccount = bill.getAccount();
        if (updateBillRequest.accountId() != null && !updateBillRequest.accountId().equals(newAccount.getId())) {
            newAccount = accountRepository.findByIdAndUserId(updateBillRequest.accountId(), userId)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
        }

        Category newCategory = bill.getCategory();
        if (updateBillRequest.categoryId() != null && !updateBillRequest.categoryId().equals(newCategory.getId())) {
            newCategory = categoryRepository.findByIdAndUserId(updateBillRequest.categoryId(), userId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        bill.updateBill(updateBillRequest, newAccount, newCategory);

        updateBillStatusIfOverdue(bill);

        return billRepository.save(bill);
    }

    @Transactional
    public void payBill(Long id, Long userId) {
        Bill bill = billRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        updateBillStatusIfOverdue(bill);

        if (bill.getStatus() == BillStatus.PAGO) {
            throw new RuntimeException("Bill already paid");
        }

        if (bill.getStatus() == BillStatus.CANCELADO) {
            throw new RuntimeException("Canceled bill cannot be paid");
        }

        Account account = bill.getAccount();

        account.setCurrentBalance(account.getCurrentBalance().subtract(bill.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setDescription("Pagamento: " + bill.getDescription());
        transaction.setAmount(bill.getAmount());
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType(TransactionType.DESPESA);
        transaction.setAccount(account);
        transaction.setCategory(bill.getCategory());
        transaction.setUser(bill.getUser());

        bill.setStatus(BillStatus.PAGO);
        bill.setPaymentDate(LocalDate.now());

        transactionRepository.save(transaction);
        accountRepository.save(account);
        billRepository.save(bill);
    }

    @Transactional
    public void cancelBill(Long id, Long userId) {
        Bill bill = billRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        updateBillStatusIfOverdue(bill);

        if (bill.getStatus() == BillStatus.PAGO) {
            throw new RuntimeException("Paid bill cannot be canceled");
        }

        if (bill.getStatus() == BillStatus.CANCELADO) {
            throw new RuntimeException("Bill is already canceled");
        }

        bill.setStatus(BillStatus.CANCELADO);
        billRepository.save(bill);
    }
}
