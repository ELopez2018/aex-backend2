/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.controllers;

import com.aex.platform.entities.*;
import com.aex.platform.repository.*;
import dtos.TransactionCreateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
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
    public ResponseEntity<?> post(@RequestBody TransactionCreateDto data) {

        Optional<User> clientO = userRepository.findById(data.getClientId());
        Optional<User> recipientO = userRepository.findById(data.getRecipientId());
        Optional<BankData>  issuingBankO = bankDataRepository.findById(data.getIssuingBankId());
        Optional<BankData>  receivingBankO = bankDataRepository.findById(data.getReceivingBankId());
        Optional<Correspondent>  correspondentO = correspondentRepository.findById(data.getCorrespondentId());
        if(!correspondentO.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error","No esta autorizado para realizar giros a terceros."));
        }
        Correspondent correspondent = correspondentO.get();
        if((correspondent.getUser().getBalance() - data.getAmountSent()) <= 0 && correspondent.getUser().getPostpaid() == false ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error","No posee saldo a favor."));
        }

        if(correspondent.getUser().getPostpaid() && (correspondent.getUser().getMaximumAmount() + (correspondent.getUser().getBalance() - data.getAmountSent() ) ) < 0){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error","No posee suficiente saldo para realizar esta operacion." + (correspondent.getUser().getMaximumAmount() + (correspondent.getUser().getBalance() - data.getAmountSent() ) )));
        }
        Transaction transaction = new Transaction();
        transaction.setClient(clientO.get());
        transaction.setRecipient(recipientO.get());
        transaction.setStatus(data.getStatus());
        transaction.setCorrespondent(correspondentO.get());
        transaction.setIssuingBank(issuingBankO.get());
        transaction.setReceivingBank(receivingBankO.get());
        transaction.setAmountReceived(data.getAmountReceived());
        transaction.setAmountSent(data.getAmountSent());
        Transaction save = transactionsRepository.save(transaction);

        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Transaction> findById = transactionsRepository.findById(id);
        if (findById.get() != null) {
            transactionsRepository.delete(findById.get());
        }
        return ResponseEntity.ok().build();
    }

}
