/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.auth.AuthenticationRequest;
import com.aex.platform.auth.AuthenticationResponse;
import com.aex.platform.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author estar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
@Tag(name = "Autenticacion")
public class AuthenticationRestController {
  private final AuthenticationService service;
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request
  ) {
    System.out.println("llego");
    return ResponseEntity.ok(service.authenticate(request));
  }
}
