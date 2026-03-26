package com.gabrieltizziani.controle_financeiro.repository;

import com.gabrieltizziani.controle_financeiro.domain.Bill;
import com.gabrieltizziani.controle_financeiro.domain.enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByIdAndUserId(Long id, Long userId);

    List<Bill> findAllByUserId(Long userId);

    List<Bill> findAllByUserIdOrderByDueDateAsc(Long userId);
    List<Bill> findAllByUserIdAndStatus(Long userId, BillStatus status);
}
