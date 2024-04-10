/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.common.Constants;
import com.aex.platform.entities.Currency;
import com.aex.platform.entities.Payment;
import com.aex.platform.entities.User;
import com.aex.platform.repository.CurrencyRepository;
import com.aex.platform.repository.UserRepository;
import com.aex.platform.service.PaymentsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author estar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(name = "Pagos")
@Log
public class PaymentsRestController {

    private final PaymentsService paymentsService;

    private final CurrencyRepository currencyRepository;

    private final UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(paymentsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(paymentsService.findByPaymentId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {

        return null;
    }

    @PostMapping()
    public ResponseEntity<?> create(
            @RequestBody MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("type") String type,
            @RequestParam("code") String code,
            @RequestParam("bank") String bank,
            @RequestParam("amount") Double amount,
            @RequestParam("status") String status,
            @RequestParam("currencyId") Long currencyId,
            @RequestParam("verifiedBy") String verifiedBy
    ) {
        log.info(Constants.BAR);
        log.info("Peticion post a /payments Recibida");
        Payment payment = new Payment();
        User user = userRepository.findById(userId).get();
        payment.setUser(user);
        payment.setType(type);
        payment.setCode(code);
        payment.setBank(bank);
        payment.setAmount(amount);
        payment.setStatus(status);
        log.info("Buscando divisa");
        Currency currency = currencyRepository.findById(currencyId).get();
        log.info("Pago en divisa: " + currency.getDescription());
        payment.setCurrencyCode(currency.getCode());
        payment.setCurrency(currency);
        payment.setVerifiedBy(verifiedBy);
        try {
            return ResponseEntity.ok().body(paymentsService.save(payment, file));
        } catch (IOException e) {
            log.info("No se guardo el Pago" + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return null;
    }

    @GetMapping("/resume")
    public ResponseEntity<?> getResumePayments(
            @RequestParam("userId") Long userId
    ) {
        log.info(Constants.BAR);
        log.info("Peticion get a /payments/resume Recibida");
        return ResponseEntity.ok().body(paymentsService.getGeneralResume(userId));
    }

    @GetMapping("/all/by-user")
    public ResponseEntity<?> getByUserId(@RequestParam("userId") Long userId, @RequestParam("currencyId") Long currencyId) {
        log.info(Constants.BAR);
        log.info("Peticion get a/all/by-user Recibida");
        log.info("currencyId: " + currencyId);
        log.info("userId: " + userId);

        return ResponseEntity.ok().body(paymentsService.findAllByUserIdAndCurrencyId(userId, currencyId));
    }

}
