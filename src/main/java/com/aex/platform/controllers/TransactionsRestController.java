/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.controllers;

import com.aex.platform.entities.*;
import com.aex.platform.entities.dtos.MobilePaymentDto;
import com.aex.platform.repository.*;
import com.aex.platform.service.MobilePaymentService;
import com.aex.platform.service.WebSocketService;
import dtos.TransactionCreateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author estar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
@Tag(name = "Transacciones (giros)")
public class TransactionsRestController {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    BankDataRepository bankDataRepository;

    @Autowired
    CorrespondentRepository correspondentRepository;

    @Autowired
    MobilePaymentService mobilePaymentService;

    @Autowired
    WebSocketService webSocketService;


    @GetMapping()
    public List<Transaction> list() {
        return transactionsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Transaction get(@PathVariable long id) {

        return transactionsRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Transaction input) {
        Transaction save = transactionsRepository.save(input);
        return ResponseEntity.ok(save);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody TransactionCreateDto[] data) {

        for(TransactionCreateDto item:data ){
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
        return ResponseEntity.ok("entro");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Transaction> findById = transactionsRepository.findById(id);
        if (findById.get() != null) {
            transactionsRepository.delete(findById.get());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllByUser/{UserId}")
    public List<Transaction> getAllByUserId(@PathVariable Long UserId) {
        return transactionsRepository.findAllByClientId(UserId);
    }

    @PostMapping(value = "/mobile-payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMobilePayment(@RequestBody List<MobilePaymentDto> data) {
        return ResponseEntity.ok().body(mobilePaymentService.create(data));
    }

    @GetMapping("/ws/inbox-update/{statusIds}")
    private ResponseEntity<?> updateTransactions(@PathVariable Long[] statusIds) {
        Map<String, String> errores = new HashMap<>();
        try {
            return ResponseEntity.ok().body(webSocketService.sendTrans(statusIds));
        } catch (Exception e) {
            errores.put("message", e.toString());
            errores.put("details", e.getMessage());
            return ResponseEntity.badRequest().body(errores);
        }

    }
}
