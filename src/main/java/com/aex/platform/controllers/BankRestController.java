/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.controllers;

import com.aex.platform.entities.Bank;
import com.aex.platform.repository.BankRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author estar
 */
@RestController
@RequestMapping("/banks")
@Tag(name = "Bancos")
public class BankRestController {
  @Autowired
  BankRepository bankRepository;

  @GetMapping()
  public List<Bank> list() {
    return bankRepository.findAll();
  }

  @GetMapping("/{id}")
  public Object get(@PathVariable String id) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> put(@PathVariable String id, @RequestBody Bank input) {
    return null;
  }

  @PostMapping
  public ResponseEntity<?> post(@RequestBody Bank input) {
    Bank save = bankRepository.save(input);
    return ResponseEntity.ok(save);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Bank input) {
    try {
      bankRepository.delete(input);
      return ResponseEntity.ok(true);
    } catch (ResponseStatusException e) {
      return null;
    }
  }
}
