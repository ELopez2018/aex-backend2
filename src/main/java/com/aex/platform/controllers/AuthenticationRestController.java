/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.auth.AuthenticationRequest;
import com.aex.platform.auth.AuthenticationResponse;
import com.aex.platform.auth.AuthenticationService;
import com.aex.platform.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
public class AuthenticationRestController {
  private final AuthenticationService service;

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    try {
      AuthenticationResponse resp = service.authenticate(request);
      //System.out.println(resp);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
    } catch (Exception e) {
      System.out.println(e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
    }

  }
}
