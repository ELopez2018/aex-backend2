/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.microservicetransactions.controllers;

import com.aex.microservicetransactions.entities.BankData;
import com.aex.microservicetransactions.entities.Client;
import com.aex.microservicetransactions.entities.Correspondent;
import com.aex.microservicetransactions.entities.Status;
import com.aex.microservicetransactions.entities.Transaction;
import com.aex.microservicetransactions.entities.User;
import com.aex.microservicetransactions.repossitory.BankDataRepository;
import com.aex.microservicetransactions.repossitory.ClientsRepository;
import com.aex.microservicetransactions.repossitory.CorrespondentRepository;
import com.aex.microservicetransactions.repossitory.StatusRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.aex.microservicetransactions.repossitory.TransactionsRepository;
import com.aex.microservicetransactions.repossitory.UserRepository;
import dtos.TransactionCreateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;

/**
 *
 * @author estar
 */
@RestController
@RequestMapping("/transactions")
@Tag(name = "Transacciones (giros)")
public class TransactionsRestController {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    ClientsRepository clientsRepository;

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

        Client client = clientsRepository.getReferenceById(data.getClientId());
        User recipient = userRepository.getReferenceById(data.getRecipientId());
        Status status = statusRepository.getById(data.getStatusId());
        User cashier = userRepository.getReferenceById(data.getCashierId());
        BankData issuingBank = bankDataRepository.getById(data.getIssuingBankId());
        BankData receivingBank = bankDataRepository.getById(data.getReceivingBankId());
        Correspondent correspondent = correspondentRepository.getById(data.getCorrespondentId());

        Transaction transaction = new Transaction();
        transaction.setClient(client);
        transaction.setRecipient(recipient);
        transaction.setStatus(status);
        transaction.setCashier(cashier);
        transaction.setCorrespondent(correspondent);
        transaction.setIssuingBank(issuingBank);
        transaction.setReceivingBank(receivingBank);
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
