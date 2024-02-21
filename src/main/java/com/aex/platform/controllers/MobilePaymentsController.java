package com.aex.platform.controllers;

import com.aex.platform.entities.MobilePayment;
import com.aex.platform.entities.User;
import com.aex.platform.service.MobilePaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mobile-payments")
@Tag(name = "Pagos - Moviles")
@Log
public class MobilePaymentsController {

    @Autowired
    private MobilePaymentService mobilePaymentService;
    @PostMapping
    public ResponseEntity<List<MobilePayment>> create(@Valid @RequestBody List<MobilePayment> data, BindingResult result) {
        log.info("Recibiendo peticion...");
        return ResponseEntity.ok().body(mobilePaymentService.create(data)) ;
    }
    @GetMapping()
    public ResponseEntity<Page<MobilePayment>> getAll(Pageable page) {
        return ResponseEntity.ok().body(mobilePaymentService.getAll(page)) ;
    }
}
