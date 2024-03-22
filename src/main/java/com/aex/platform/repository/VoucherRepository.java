package com.aex.platform.repository;

import com.aex.platform.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    Optional<List<Voucher>> findAllByTransactionId(Long transactionId);

    Optional<List<Voucher>> findAllByMobilePaymentId(Long transactionId);
}
