package com.aex.platform.interfaces;

import com.aex.platform.entities.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentInterface {
    Payment save(Payment payment);

    List<Payment> saveAll(List<Payment> payments);

    Optional<Payment> findByPaymentId(Long paymentId);

    List<Payment> findAll();

    boolean deleteById(Long Id);

    boolean deleteAll(List<Long> Ids);


    Payment update(Payment payment);
}
