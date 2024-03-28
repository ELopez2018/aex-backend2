package com.aex.platform.service;

import com.aex.platform.entities.Currency;
import com.aex.platform.entities.Payment;
import com.aex.platform.entities.dtos.PaymentResumeDto;
import com.aex.platform.interfaces.PaymentInterface;
import com.aex.platform.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Log
@Service
@RequiredArgsConstructor
public class PaymentsService implements PaymentInterface {

    private final FileStorageService fileStorageService;

    private final PaymentsRepository paymentsRepository;

    private final BalanceService balanceService;

    private final CurrencyService currencyService;

    @Override
    public Payment save(Payment payment, MultipartFile file) throws IOException {
        log.info("Guardando pago");
        Payment paymentSaved = paymentsRepository.save(payment);
        Path path = fileStorageService.save("/payments", file, String.valueOf(payment.getUser().getDocumentNumber()), paymentSaved.getId());
        paymentSaved.setFile(path.toString());
        log.info("Pago Guardado");
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

    @Override
    public List<PaymentResumeDto> getResumePayments(Long userId) {
        List<PaymentResumeDto> resumePayment  = paymentsRepository.getResumePayments(userId);
        List<PaymentResumeDto> resumePayment2 = balanceService.getBalanceCashier(userId);
        return resumePayment2;
    }

    @Override
    public List<Payment> findAllByUserIdAndCurrencyId(Long userId, Long currecyId) {
        log.info("Buscando divisa");
        Currency currency = currencyService.findByCurrencyId(currecyId).get();
        log.info("Divisa encontrada " + currency.getDescription());
        return paymentsRepository.findAllByUserIdAndCurrencyId(userId, currency.getId());
    }
}
