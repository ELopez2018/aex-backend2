package com.aex.platform.service;

import com.aex.platform.controllers.WebSocketClient;
import com.aex.platform.entities.*;
import com.aex.platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private MobilePaymentRepository mobilePaymentRepository;

    @Autowired
    private WebSocketClient webSocketClient;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    BankDataRepository bankDataRepository;

    @Autowired
    CorrespondentRepository correspondentRepository;


    public Boolean updateWebsocketTransactionsTodo(Collection<Long> statusIds) {
        List<Transaction> giros = transactionsRepository.findAllByStatusIn(statusIds);
        List<MobilePayment> pagosMobiles = mobilePaymentRepository.findAllByStatusIn(statusIds);
        List<TransactionTodo> arr =transactionTodoAdapter(giros, pagosMobiles);
        return webSocketClient.transactionPending(arr).getBody();
    }

    private List<TransactionTodo> transactionTodoAdapter(List<Transaction> giros, List<MobilePayment> pagosMobiles) {
        List<TransactionTodo> transactionTodoList = new ArrayList<>();
        for (MobilePayment item: pagosMobiles){
            transactionTodoList.add(convertMPToTtl(item));
        }
        for (Transaction item: giros){
            transactionTodoList.add(convertGiroToTtl(item));
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

    public  ResponseEntity<?> create(dtos.TransactionCreateDto[] data){
        for(dtos.TransactionCreateDto item:data ){
            Optional<User> clientO = userRepository.findById(item.getClientId());
            Optional<User> recipientO = userRepository.findById(item.getRecipientId());
            Optional<BankData> receivingBankO = bankDataRepository.findById(item.getReceivingBankId());
            Optional<Correspondent> correspondentO = correspondentRepository.findById(item.getCorrespondentId());
            if (!correspondentO.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "No esta autorizado para realizar giros a terceros."));
            }
            Correspondent correspondent = correspondentO.get();
            if ((correspondent.getUser().getBalance() - item.getAmountSent()) <= 0 && correspondent.getUser().getPostpaid() == false) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "No posee saldo a favor."));
            }

            if (correspondent.getUser().getPostpaid() && (correspondent.getUser().getMaximumAmount() + (correspondent.getUser().getBalance() - item.getAmountSent())) < 0) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "No posee suficiente saldo para realizar esta operacion." + (correspondent.getUser().getMaximumAmount() + (correspondent.getUser().getBalance() - item.getAmountSent()))));
            }

            Transaction transaction = new Transaction();
            transaction.setClient(clientO.get());
            transaction.setRecipient(recipientO.get());
            transaction.setStatus(statusRepository.findById(1L).get().getId());
            transaction.setCorrespondent(correspondentO.get());
            transaction.setReceivingBank(receivingBankO.get());
            transaction.setAmountReceived(item.getAmountReceived());
            transaction.setAmountSent(item.getAmountSent());
            Transaction save = transactionsRepository.save(transaction);
        }
       updateWebsocketTransactionsTodo(List.of(1L));
        return  ResponseEntity.ok().body(Collections.singletonMap("message", "Se crearon los giros exitosamente"));
    }
}
