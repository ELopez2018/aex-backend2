/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.controllers;

import com.aex.platform.entities.BankData;
import com.aex.platform.entities.User;
import com.aex.platform.repository.BankDataRepository;
import com.aex.platform.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author estar
 */
@RestController
@RequestMapping("/bankdata")
@Tag(name = "Datos bancarios")
public class BankDataRestController {

    @Autowired
    BankDataRepository bankDataRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public List<BankData> list() {
        return bankDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public BankData get(@PathVariable String id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody BankData input, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        try {
            BankData save = bankDataRepository.save(input);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return null;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBankDatabyUserId(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<List<BankData>> optionalBankData = bankDataRepository.findAllByUserId(userId);

        if (optionalBankData.isPresent()) {
            return ResponseEntity.ok().body(optionalBankData.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
