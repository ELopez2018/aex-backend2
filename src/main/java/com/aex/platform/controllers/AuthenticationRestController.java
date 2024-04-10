/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.auth.AuthenticationRequest;
import com.aex.platform.auth.AuthenticationResponse;
import com.aex.platform.auth.AuthenticationService;
import com.aex.platform.common.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author estar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
@Tag(name = "Autenticacion")
@Log
public class AuthenticationRestController {
    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        log.info(Constants.BAR);
        log.info("Logeo");
        try {
            AuthenticationResponse resp = service.authenticate(request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }

    }
}
