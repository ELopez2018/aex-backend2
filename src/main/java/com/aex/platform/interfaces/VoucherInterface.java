package com.aex.platform.interfaces;

import com.aex.platform.entities.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherInterface {

    Voucher save(Voucher voucher);

    List<Voucher> saveAll(List<Voucher> voucher);

    Optional<List<Voucher>> findByMobilePaymentId(Long mobilPayId);
    Optional<List<Voucher>> findTransactionId(Long transactionId);

    List<Voucher> findAll();

    boolean deleteById(Long Id);

    boolean deleteAll(List<Long> Ids);


    Voucher update(Voucher voucher);
}
