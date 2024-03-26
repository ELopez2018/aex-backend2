package com.aex.platform.service;

import com.aex.platform.entities.Currency;
import com.aex.platform.entities.MobilePayment;
import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.MobilePaymentDto;
import com.aex.platform.entities.dtos.UserDTO;
import com.aex.platform.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        Double paymentsTotal =0.0;
        Double transactionsTotal = 0.0;
        Double mobilePaymentsTotal = 0.0;

        Currency currency = currencyRepository.findById(currencyId).get();

        if(  paymentsRepository.getPaymentsTotal(userId, currency.getId()) != null){
            paymentsTotal = paymentsRepository.getPaymentsTotal(userId, currency.getId());
        }

        if(  transactionsRepository.getTransactionTotal(userId, currency.getCode()) != null){
            transactionsTotal = transactionsRepository.getTransactionTotal(userId, currency.getCode());
        }

        if(  mobilePaymentRepository.getMobilePaymentnTotal(userId) != null){
            mobilePaymentsTotal = mobilePaymentRepository.getMobilePaymentnTotal(userId);
        }

        balance = paymentsTotal - (transactionsTotal + mobilePaymentsTotal);
        User user = userRepository.findById(userId).get();
        user.setBalance(balance);
        ModelMapper modelMapper = new ModelMapper();
        userRepository.save(user);
        UserDTO userDto =  modelMapper.map(user, UserDTO.class);
        return userDto;
    }

    public UserDTO getBalanceByCurrency(Long userId, Currency currency) {
        Double balance = 0.0;
        Double paymentsTotal =0.0;
        Double transactionsTotal = 0.0;
        Double mobilePaymentsTotal = 0.0;

        if(  paymentsRepository.getPaymentsTotal(userId,currency.getId() ) != null){
            paymentsTotal = paymentsRepository.getPaymentsTotal(userId, currency.getId());
        }

        if(  transactionsRepository.getTransactionTotal(userId, currency.getCode()) != null){
            transactionsTotal = transactionsRepository.getTransactionTotalCashierByCurrency(userId,currency.getCode());
        }

        if(  mobilePaymentRepository.getMobilePaymentnTotal(userId) != null){
            mobilePaymentsTotal = mobilePaymentRepository.getMobilePaymentnTotal(userId);
        }

        balance = paymentsTotal - (transactionsTotal + mobilePaymentsTotal);
        User user = userRepository.findById(userId).get();
        user.setBalance(balance);
        ModelMapper modelMapper = new ModelMapper();
        userRepository.save(user);
        UserDTO userDto =  modelMapper.map(user, UserDTO.class);
        return userDto;
    }
    public void getBalanceCashier(){

    }
}
