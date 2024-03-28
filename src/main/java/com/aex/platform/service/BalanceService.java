package com.aex.platform.service;

import com.aex.platform.entities.Currency;
import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.PaymentResumeDto;
import com.aex.platform.entities.dtos.UserDTO;
import com.aex.platform.repository.*;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class BalanceService {
    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    MobilePaymentRepository mobilePaymentRepository;

    @Autowired
    PaymentsRepository paymentsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrencyRepository currencyRepository;


    public UserDTO getBalance(Long userId, Long currencyId) {
        Double balance = 0.0;
        Double paymentsTotal = 0.0;
        Double transactionsTotal = 0.0;
        Double mobilePaymentsTotal = 0.0;

        Currency currency = currencyRepository.findById(currencyId).get();

        if (paymentsRepository.getPaymentsTotal(userId, currency.getId()) != null) {
            paymentsTotal = paymentsRepository.getPaymentsTotal(userId, currency.getId());
        }

        if (transactionsRepository.getTransactionTotal(userId, currency.getCode()) != null) {
            transactionsTotal = transactionsRepository.getTransactionTotal(userId, currency.getCode());
        }

        if (mobilePaymentRepository.getMobilePaymentnTotal(userId, currency.getCode()) != null) {
            mobilePaymentsTotal = mobilePaymentRepository.getMobilePaymentnTotal(userId, currency.getCode());
        }

        balance = paymentsTotal - (transactionsTotal + mobilePaymentsTotal);
        User user = userRepository.findById(userId).get();
        user.setBalance(balance);
        ModelMapper modelMapper = new ModelMapper();
        userRepository.save(user);
        UserDTO userDto = modelMapper.map(user, UserDTO.class);
        return userDto;
    }

    public UserDTO getBalanceByCurrency(Long userId, Currency currency) {
        Double balance = 0.0;
        Double paymentsTotal = 0.0;
        Double transactionsTotal = 0.0;
        Double mobilePaymentsTotal = 0.0;

        if (paymentsRepository.getPaymentsTotal(userId, currency.getId()) != null) {
            paymentsTotal = paymentsRepository.getPaymentsTotal(userId, currency.getId());
        }

        if (transactionsRepository.getTransactionTotal(userId, currency.getCode()) != null) {
            transactionsTotal = transactionsRepository.getTransactionTotalCashierByCurrency(userId, currency.getCode());
        }

        if (mobilePaymentRepository.getMobilePaymentnTotal(userId, currency.getCode()) != null) {
            mobilePaymentsTotal = mobilePaymentRepository.getMobilePaymentnTotal(userId, currency.getCode());
        }

        balance = paymentsTotal - (transactionsTotal + mobilePaymentsTotal);
        User user = userRepository.findById(userId).get();
        user.setBalance(balance);
        ModelMapper modelMapper = new ModelMapper();
        userRepository.save(user);
        UserDTO userDto = modelMapper.map(user, UserDTO.class);
        return userDto;
    }

    public List<PaymentResumeDto> getBalanceCashier(Long userId) {
        Double balance = 0.0;
        Double paymentsTotal = 0.0;
        Double transactionsTotal = 0.0;
        Double transactionsTotalCashier = 0.0;
        Double mobilePaymentsTotal = 0.0;
        Double mobilePaymentsTotalCashier = 0.0;
        List<PaymentResumeDto> arrayList = new ArrayList<>();
        log.info("Obteniendo el total d los pagos en diferentes divisas");
        List<PaymentResumeDto> paymentResume = paymentsRepository.getResumePayments(userId);

        for (PaymentResumeDto payment : paymentResume) {

            log.info("Obteniendo el total d los pagos en diferentes divisas como corresponsal");
            transactionsTotal = transactionsRepository.getTransactionTotal(userId, payment.getCurrencyCode());
            log.info("Obteniendo el total d los pagos en giros" +  payment.getCurrencyCode() +" total : "+ transactionsTotal);
            if (transactionsTotal == null) {
                transactionsTotal = 0.0;
            }

            mobilePaymentsTotal = mobilePaymentRepository.getMobilePaymentnTotal(userId, payment.getCurrencyCode());
            log.info("Obteniendo el total d los pagos en pagos moviles");
            if (mobilePaymentsTotal == null) {
                mobilePaymentsTotal = 0.0;
            }

            log.info("Obteniendo el total d los pagos en diferentes divisas como cajero");
            transactionsTotalCashier = transactionsRepository.getTransactionTotalCashierByCurrency(userId, payment.getCurrencyCode());
            log.info("Obteniendo el total d los pagos en giros como cajero");
            if (transactionsTotalCashier == null) {
                transactionsTotalCashier = 0.0;
            }
            mobilePaymentsTotalCashier = mobilePaymentRepository.getMobilePaymentnByCashier(userId, payment.getCurrencyCode());
            log.info("Obteniendo el total d los pagos en pagos moviles como cajero");
            if (mobilePaymentsTotalCashier == null) {
                mobilePaymentsTotalCashier = 0.0;
            }
            payment.setBalance(payment.getTotaPaid() - (transactionsTotal + mobilePaymentsTotal + transactionsTotalCashier + mobilePaymentsTotalCashier));
            payment.setTotalSpent(transactionsTotal + mobilePaymentsTotal + transactionsTotalCashier + mobilePaymentsTotalCashier);
            arrayList.add(payment);
        }
        return arrayList;
    }
}
