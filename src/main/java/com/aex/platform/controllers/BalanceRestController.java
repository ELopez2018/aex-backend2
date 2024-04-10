package com.aex.platform.controllers;

import com.aex.platform.common.Constants;
import com.aex.platform.service.BalanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
@Tag(name = "Saldos y Totales")
@Log
public class BalanceRestController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/by-cashier")
    public ResponseEntity<?> getAllOperationsByCashierIdByCurrencyId(@RequestParam Long userId, @RequestParam Long currencyId) {
        log.info(Constants.BAR);
        log.info("Peticion get a /balance/by-cashier recibida");
        return ResponseEntity.status(HttpStatus.OK)
                .body(balanceService.getAllOperationsByCashier(userId, currencyId));
    }
}
