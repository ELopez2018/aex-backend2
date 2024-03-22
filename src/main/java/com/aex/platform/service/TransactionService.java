package com.aex.platform.service;

import com.aex.platform.common.Constants;
import com.aex.platform.controllers.WebSocketClient;
import com.aex.platform.entities.*;
import com.aex.platform.repository.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log
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
        log.info(Constants.BAR);
        log.info("Recibiendo  Ids " + statusIds);
        List<Transaction> giros = transactionsRepository.findAllByStatusIn(statusIds);
        log.info(giros.size() + " GIROS ENCONTRADOS");
        List<MobilePayment> pagosMobiles = mobilePaymentRepository.findAllByStatusIn(statusIds);
        log.info(pagosMobiles.size() + " PAGOS MOVILES ENCONTRADOS");
        List<TransactionTodo> arr = transactionTodoAdapter(giros, pagosMobiles);
        log.info(arr.size() + " TRANSACCIONES EN TOTAL");
        if (arr.isEmpty()) {
            log.info("No hay transacciones para enviar");
            return false;
        }
        try {
            if (statusIds.size() == 1) {
                long statusId = statusIds.stream().findFirst().orElse(null);
                if (statusId == 1L) {
                    log.info("Estatus Recibido: Transacciones pendientes");
                    log.info("Enviando datos al Socket");
                    return webSocketClient.transactionPending(arr);
                } else if (statusId == 6L) {
                    log.info("Estatus Recibido:  Transacciones en Progreso");
                    log.info("Enviando datos al Socket");
                    return webSocketClient.transactionInProgress(arr);
                }
            }
            return webSocketClient.transactionPending(arr);
        } catch (FeignException e) {
            log.severe("[updateWebsocketTransactionsTodo]:Error" + e.getMessage());
            log.severe(e.getLocalizedMessage());
            return false;
        }

    }

    private List<TransactionTodo> transactionTodoAdapter(List<Transaction> giros, List<MobilePayment> pagosMobiles) {
        List<TransactionTodo> transactionTodoList = new ArrayList<>();
        for (MobilePayment item : pagosMobiles) {
            transactionTodoList.add(convertMPToTtl(item));
        }
        for (Transaction item : giros) {
            transactionTodoList.add(convertGiroToTtl(item));
        }
        return transactionTodoList;
    }

    private TransactionTodo convertGiroToTtl(Transaction giro) {
        TransactionTodo ttdo = new TransactionTodo();
        ttdo.setBank(giro.getReceivingBank().getBank().getName());
        ttdo.setId(giro.getId());
        ttdo.setCorrespondent(giro.getCorrespondent().getTradename());
        ttdo.setBenficiary(giro.getRecipient().getFullName());
        ttdo.setStatus(giro.getStatus());
        ttdo.setValue(giro.getAmountReceived());
        ttdo.setTransactionType(1l);
        ttdo.setBankAccount(giro.getReceivingBank().getAccountNumber());
        ttdo.setType(giro.getReceivingBank().getType());
        ttdo.setDni(giro.getRecipient().getDocumentNumber());
        if (giro.getCashier() != null) {
            ttdo.setCashierName(giro.getCashier().getFullName());
            ttdo.setCashier(giro.getCashier().getId());
        }
        return ttdo;
    }

    private TransactionTodo convertMPToTtl(MobilePayment mobilePayment) {
        TransactionTodo ttdo = new TransactionTodo();
        ttdo.setId(mobilePayment.getId());
        ttdo.setTransactionType(2l);
        ttdo.setBank(mobilePayment.getBank().getName());
        ttdo.setCorrespondent(mobilePayment.getCorrespondent().getTradename());
        ttdo.setBenficiary(mobilePayment.getDocumentNumber());
        ttdo.setCellPhone(mobilePayment.getCellPhoneNumber());
        ttdo.setStatus(mobilePayment.getStatus());
        ttdo.setValue(mobilePayment.getValueReceive());
        ttdo.setDni(Long.valueOf(mobilePayment.getDocumentNumber()));
        if (mobilePayment.getCashier() != null) {
            ttdo.setCashierName(mobilePayment.getCashier().getFullName());
            ttdo.setCashier(mobilePayment.getCashier().getId());
        }
        return ttdo;
    }

    public ResponseEntity<?> create(dtos.TransactionCreateDto[] data) {
        for (dtos.TransactionCreateDto item : data) {
            Optional<User> clientO = userRepository.findById(item.getClientId());
            Optional<User> recipientO = userRepository.findById(item.getRecipientId());
            Optional<BankData> receivingBankO = bankDataRepository.findById(item.getReceivingBankId());
            Optional<Correspondent> correspondentO = correspondentRepository.findById(item.getCorrespondentId());
            if (correspondentO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "No esta autorizado para realizar giros a terceros."));
            }
            Correspondent correspondent = correspondentO.get();
            if ((correspondent.getUser().getBalance() - item.getAmountSent()) <= 0 && !correspondent.getUser().getPostpaid()) {
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
            transaction.setDateSend(item.getDateSend());
            transaction.setCurrencyFrom(item.getCurrencyFrom());
            transaction.setCurrencyTo(item.getCurrencyTo());
            transaction.setReference(item.getReference());
            Transaction save = transactionsRepository.save(transaction);
        }
        updateWebsocketTransactionsTodo(List.of(1L));
        return ResponseEntity.ok().body(Collections.singletonMap("message", "Se crearon los giros exitosamente"));
    }

    public Transaction save(Transaction transaction) {
        log.info("Guardando cambios");
        return transactionsRepository.save(transaction);
    }
}
