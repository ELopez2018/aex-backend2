package com.aex.platform.interfaces;

import com.aex.platform.entities.Payment;
import com.aex.platform.entities.dtos.PaymentResumeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PaymentInterface {
    Payment save(Payment payment, MultipartFile file) throws IOException;

    List<Payment> saveAll(List<Payment> payments);

    Optional<Payment> findByPaymentId(Long paymentId);

    List<Payment> findAll();

    boolean deleteById(Long Id);

    boolean deleteAll(List<Long> Ids);
    Payment update(Payment payment);

    List<PaymentResumeDto> getResumePayments(Long userId);

    List<Payment> findAllByUserIdAndCurrencyId(Long userId, Long currecyId);
}
