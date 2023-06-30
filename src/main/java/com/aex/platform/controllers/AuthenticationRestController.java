/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.afrac.serviceorders.controllers;

import com.afrac.serviceorders.auth.AuthenticationRequest;
import com.afrac.serviceorders.auth.AuthenticationResponse;
import com.afrac.serviceorders.auth.AuthenticationService;
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
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationRestController {
  private final AuthenticationService service;
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request
  ) {

    return ResponseEntity.ok(service.authenticate(request));
  }
}
