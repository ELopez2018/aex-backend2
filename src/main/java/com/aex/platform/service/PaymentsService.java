package com.aex.platform.service;

import com.aex.platform.entities.Payment;
import com.aex.platform.interfaces.PaymentInterface;
import com.aex.platform.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log
@Service
@RequiredArgsConstructor
public class PaymentsService implements PaymentInterface {
    private final PaymentsRepository paymentsRepository;
    @Override
    public Payment save(Payment payment) {
        return paymentsRepository.save(payment);
    }

    @Override
    public List<Payment> saveAll(List<Payment> payments) {
        return paymentsRepository.saveAll(payments);
    }

    @Override
    public Optional<Payment> findByPaymentId(Long paymentId) {
        return paymentsRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findAll() {
        return paymentsRepository.findAll();
    }

    @Override
    public boolean deleteById(Long Id) {
        return false;
    }

    @Override
    public boolean deleteAll(List<Long> Ids) {
        return false;
    }

    @Override
    public Payment update(Payment payment) {
        return paymentsRepository.save(payment);
    }
}
