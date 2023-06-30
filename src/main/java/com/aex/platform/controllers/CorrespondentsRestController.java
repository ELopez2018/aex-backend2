/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.entities.Correspondent;
import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.CorrespondentDTO;
import com.aex.platform.repository.CorrespondentRepository;
import com.aex.platform.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author estar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/correspondents")
@Tag(name = "Corresponsales")
public class CorrespondentsRestController {

  @Autowired
  UserRepository userRepository;
  private final CorrespondentRepository correspondentRepository;

  @GetMapping()
  public List<Object> list() {
    return null;
  }

  @GetMapping("/{id}")
  public Object get(@PathVariable String id) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> put(@PathVariable String id, @RequestBody Object input) {
    return null;
  }

  @PostMapping
  public ResponseEntity<?> post(@RequestBody Correspondent input) {
    Optional<User> userOptional = userRepository.findById(input.getUser().getId());
    if (!userOptional.isPresent()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "El usuario no existe"));
    }

    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(correspondentRepository.save(input));
    } catch (Exception e) {
      System.out.println("Error");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", e.toString()));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    return null;
  }

}
