package com.gabrieltizziani.controle_financeiro.controller;

import com.gabrieltizziani.controle_financeiro.domain.Bill;
import com.gabrieltizziani.controle_financeiro.domain.User;
import com.gabrieltizziani.controle_financeiro.dto.bill.BillResponse;
import com.gabrieltizziani.controle_financeiro.dto.bill.CreateBillRequest;
import com.gabrieltizziani.controle_financeiro.dto.bill.UpdateBillRequest;
import com.gabrieltizziani.controle_financeiro.service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<BillResponse> createBill(
            @RequestBody @Valid CreateBillRequest createBillRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Bill bill = billService.createBill(createBillRequest, user.getId());
        return ResponseEntity.ok(new BillResponse(bill));
    }

    @GetMapping
    public ResponseEntity<List<BillResponse>> findAllBills(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(billService.findAllBills(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> findBillById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(billService.findBillById(id, user.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillResponse> updateBill(
            @PathVariable Long id,
            @RequestBody @Valid UpdateBillRequest updateBillRequest,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Bill bill = billService.updateBill(id, updateBillRequest, user.getId());
        return ResponseEntity.ok(new BillResponse(bill));
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<Void> payBill(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        billService.payBill(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBill(
            @PathVariable Long id,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        billService.cancelBill(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
