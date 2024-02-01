package com.aex.platform.service;

import com.aex.platform.controllers.WebSocketClient;
import com.aex.platform.entities.MobilePayment;
import com.aex.platform.entities.Transaction;
import com.aex.platform.entities.TransactionTodo;
import com.aex.platform.repository.MobilePaymentRepository;
import com.aex.platform.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private MobilePaymentRepository mobilePaymentRepository;

    @Autowired
    private WebSocketClient webSocketClient;

    public ResponseEntity<Boolean> updateWebsocketTransactionsTodo(Collection<Long> statusIds) {
        List<Transaction> giros = transactionsRepository.findAllByStatusIn(statusIds);
        List<MobilePayment> pagosMobiles = mobilePaymentRepository.findAllByStatusIn(statusIds);
        List<TransactionTodo> arr =transactionTodoAdapter(giros, pagosMobiles);
        return webSocketClient.transactionPending(arr);
    }

    private List<TransactionTodo> transactionTodoAdapter(List<Transaction> giros, List<MobilePayment> pagosMobiles) {
        List<TransactionTodo> transactionTodoList = new ArrayList<>();
        for (Transaction item: giros){
            transactionTodoList.add(convertGiroToTtl(item));
        }
        for (MobilePayment item: pagosMobiles){
            transactionTodoList.add(convertMPToTtl(item));
        }
        return transactionTodoList;
    }
    private TransactionTodo convertGiroToTtl(Transaction giro){
        TransactionTodo ttdo = new TransactionTodo();
        ttdo.setBank(giro.getReceivingBank().getBank().getName());
        ttdo.setId(giro.getId());
        ttdo.setCorrespondent(giro.getCorrespondent().getTradename());
        ttdo.setBenficiary(giro.getRecipient().getFullName());
        ttdo.setStatus(giro.getStatus());
        ttdo.setValue(giro.getAmountReceived());
        ttdo.setTransactionType(1l);
        return  ttdo;
    }

    private TransactionTodo convertMPToTtl(MobilePayment mobilePayment){
        TransactionTodo ttdo = new TransactionTodo();
        ttdo.setId(mobilePayment.getId());
        ttdo.setTransactionType(2l);
        ttdo.setBank(mobilePayment.getBank().getName());
        ttdo.setCorrespondent(mobilePayment.getCorrespondent().getTradename());
        ttdo.setBenficiary(mobilePayment.getDocumentNumber());
        ttdo.setCellPhone(mobilePayment.getCellPhoneNumber());
        ttdo.setStatus(mobilePayment.getStatus());
        ttdo.setValue(mobilePayment.getValueReceive());
        return  ttdo;
    }
}
