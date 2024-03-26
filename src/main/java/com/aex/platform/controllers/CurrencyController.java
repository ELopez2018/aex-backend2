package com.aex.platform.controllers;

import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.UserAdapter;
import com.aex.platform.service.CurrencyService;
import com.aex.platform.service.MobilePaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
@Tag(name = "Monedas")
@Log
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<?> findById() {
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.findAll());
    }
}
