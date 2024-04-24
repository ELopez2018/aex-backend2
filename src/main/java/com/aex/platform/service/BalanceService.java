package com.aex.platform.service;

import com.aex.platform.entities.*;
import com.aex.platform.entities.dtos.PaymentResumeDto;
import com.aex.platform.entities.dtos.UserDTO;
import com.aex.platform.repository.*;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    TransactionService transactionService;


    public UserDTO getBalance(Long userId, Long currencyId) {
        log.info("Obteniendo Saldo");
        Double balance = 0.0;
        Double paymentsTotal = 0.0;
        Double transactionsTotal = 0.0;
        Double mobilePaymentsTotal = 0.0;

        Currency currency = currencyRepository.findById(currencyId).orElse(null);
        if (currency != null && paymentsRepository.getPaymentsTotal(userId, currency.getId()) != null) {
            paymentsTotal = paymentsRepository.getPaymentsTotal(userId, currency.getId());
        }
        log.info("Pagos totales: " + paymentsTotal);
        if (currency != null && transactionsRepository.getTransactionTotal(userId, currency.getCode()) != null) {
            transactionsTotal = transactionsRepository.getTransactionTotal(userId, currency.getCode());
        }
        log.info("Giros totales: " + transactionsTotal);

        if (currency != null && mobilePaymentRepository.getMobilePaymentnTotal(userId, currency.getCode()) != null) {
            mobilePaymentsTotal = mobilePaymentRepository.getMobilePaymentnTotal(userId, currency.getCode());
        }
        log.info("Pago Movil totales: " + mobilePaymentsTotal);

        balance = paymentsTotal - (transactionsTotal + mobilePaymentsTotal);

        log.info("===================");
        log.info("= Saldo : " + balance);
        log.info("===================");

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

    public List<PaymentResumeDto> getGeneralResume(Long userId) {
        Double balance = 0.0;
        Double paymentsTotal = 0.0;
        Double transactionsTotal = 0.0;
        Double transactionsTotalCashier = 0.0;
        Double mobilePaymentsTotal = 0.0;
        Double mobilePaymentsTotalCashier = 0.0;
        List<PaymentResumeDto> arrayList = new ArrayList<>();

        log.info("Obteniendo el total de los pagos en diferentes divisas");
        List<PaymentResumeDto> paymentResume = paymentsRepository.getResumePayments(userId);

        for (PaymentResumeDto payment : paymentResume) {

            log.info("Obteniendo el total de los Giros por divisas como corresponsal");
            transactionsTotal = transactionsRepository.getTransactionTotal(userId, payment.getCurrencyCode());
            if (transactionsTotal == null) {
                transactionsTotal = 0.0;
            }
            log.info("Total de los Giros como corresponsal: " +  transactionsTotal);

            log.info("Obteniendo el total de los pagos en pagos moviles  como corresponsal");
            mobilePaymentsTotal = mobilePaymentRepository.getMobilePaymentnTotal(userId, payment.getCurrencyCode());

            if (mobilePaymentsTotal == null) {
                mobilePaymentsTotal = 0.0;
            }
            log.info("Pagos Moviles como corresponsal: " + mobilePaymentsTotal);


            log.info("Obteniendo el total de los giros por divisas como cajero");
            transactionsTotalCashier = transactionsRepository.getTransactionTotalCashierByCurrency(userId, payment.getCurrencyCode());
            if (transactionsTotalCashier == null) {
                transactionsTotalCashier = 0.0;
            }
            log.info("Giros como cajero: " + transactionsTotalCashier);

            log.info("Obteniendo el total de los Pagos Moviles por divisas como cajero");
            mobilePaymentsTotalCashier = mobilePaymentRepository.getMobilePaymentnByCashier(userId, payment.getCurrencyCode());
            if (mobilePaymentsTotalCashier == null) {
                mobilePaymentsTotalCashier = 0.0;
            }
            log.info("Pagos Moviles por divisas como cajero: " + mobilePaymentsTotalCashier);

            payment.setBalance(payment.getTotaPaid() - (transactionsTotal + mobilePaymentsTotal + transactionsTotalCashier + mobilePaymentsTotalCashier));
            payment.setTotalSpent(transactionsTotal + mobilePaymentsTotal + transactionsTotalCashier + mobilePaymentsTotalCashier);
            arrayList.add(payment);
        }
        return arrayList;
    }

    public List<TransactionTodo> getAllOperationsByCashier(Long userId, Long currencyId) {
        List<TransactionTodo> transactionTodoList = new ArrayList<>();
        Currency currency = currencyRepository.findById(currencyId).get();
        log.info("Obteniendo lista de giros como cajero");
       Optional<List<Transaction>> optionalTransactions = transactionsRepository.getTransactionByCashierByCurrency(userId,currency.getCode());
        log.info("Registros obtenidos: " + optionalTransactions.get().size());
        log.info("");
        log.info("Obteniendo lista de Pagos Moviles como cajero");
        Optional<List<MobilePayment>> optionalMobilePayments = mobilePaymentRepository.getAllMobilePaymentnByCashier(userId,currency.getCode());
        log.info("Registros obtenidos: " + optionalMobilePayments.get().size());
        log.info("");
        transactionTodoList = transactionService.transactionTodoAdapter(optionalTransactions.get(), optionalMobilePayments.get());

        log.info("Obteniendo lista de giros como corresponsal");
        Optional<List<Transaction>> optionalTransactionList = transactionsRepository.getTransactionByCorrespondentByCurrency(userId, currency.getCode());
        log.info("Registros obtenidos: " + optionalTransactionList.get().size());
        log.info("");

        log.info("Obteniendo lista de Pagos Moviles como corresponsal");
        log.info("userId: " + userId);
        log.info("currencyCode: " + currency.getCode());
        List<MobilePayment> mobilePaymentsTodoCorrespond = mobilePaymentRepository.getAllMobilePaymentnByCorrespondent(userId, currency.getCode()).get();
        log.info("Registros obtenidos: " + mobilePaymentsTodoCorrespond.size());
        log.info("");

        List<TransactionTodo> transactionTodoCorrespond = transactionService.transactionTodoAdapterByCorrespondent(optionalTransactionList.get(), mobilePaymentsTodoCorrespond );
        transactionTodoList.addAll(transactionTodoCorrespond);

        return  transactionTodoList;
    }
}
