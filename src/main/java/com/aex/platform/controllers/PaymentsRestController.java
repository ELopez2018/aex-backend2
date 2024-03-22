/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.entities.Payment;
import com.aex.platform.repository.PaymentsRepository;
import com.aex.platform.service.PaymentsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author estar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(name = "Pagos")
public class PaymentsRestController {

    private final PaymentsService paymentsService;
    
    @GetMapping()
    public  ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(paymentsService.findAll()) ;
    }
    
    @GetMapping("/{id}")
    public  ResponseEntity<?>  get(@PathVariable Long id) {
        return ResponseEntity.ok().body(paymentsService.findByPaymentId(id)) ;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {

        return null;
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Payment payment) {
        return ResponseEntity.ok().body(paymentsService.save(payment)) ;

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return null;
    }
    
}
