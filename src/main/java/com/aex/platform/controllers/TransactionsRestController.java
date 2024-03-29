/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.controllers;

import com.aex.platform.common.Constants;
import com.aex.platform.entities.*;
import com.aex.platform.entities.dtos.MobilePaymentDto;
import com.aex.platform.repository.*;
import com.aex.platform.service.MobilePaymentService;
import com.aex.platform.service.TransactionService;
import com.aex.platform.service.WebSocketService;
import dtos.TransactionCreateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author estar
 */
@Log
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

    @Autowired
    TransactionService transactionService;


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
        return transactionService.create(data);
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
    public ResponseEntity<?> createMobilePayment(@RequestBody List<MobilePayment> data) {
        return ResponseEntity.ok().body(mobilePaymentService.create(data));
    }

    @GetMapping("/ws/inbox-update/{statusIds}")
    private ResponseEntity<?> updateTransactions(@PathVariable Long[] statusIds) {
        Map<String, String> errores = new HashMap<>();
        try {
            if (webSocketService.updateInfoWebSocket(statusIds)) {
                return ResponseEntity.ok().body(Collections.singletonMap("message", "Actualización exitosa"));
            } else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Un error impidio la consulta"));
            }
        } catch (Exception e) {
            errores.put("message", e.toString());
            errores.put("details", e.getMessage());
            return ResponseEntity.badRequest().body(errores);
        }

    }
    @PatchMapping(value = "/update-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatus(@RequestBody TransactionTodo transactionTodo) {
        log.info(Constants.BAR);
        log.info("Peticion de actualizacio parcial recibida");
        return ResponseEntity.ok().body(mobilePaymentService.updateStatusTransaction(transactionTodo));
    }

    @GetMapping("/all/bystatus/{statusIds}")
    private ResponseEntity<?> getAllByStatus(@PathVariable Long[] statusIds) {
        Map<String, String> errores = new HashMap<>();
        try {
            if (webSocketService.updateInfoWebSocket(statusIds)) {
                return ResponseEntity.ok().body(Collections.singletonMap("message", "Actualización exitosa"));
            } else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Un error impidio la consulta"));
            }
        } catch (Exception e) {
            errores.put("message", e.toString());
            errores.put("details", e.getMessage());
            return ResponseEntity.badRequest().body(errores);
        }

    }
}
