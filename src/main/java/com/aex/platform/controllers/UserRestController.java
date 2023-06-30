/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.auth.AuthenticationResponse;
import com.aex.platform.common.Utils;
import com.aex.platform.entities.Role;
import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.UserAdapter;
import com.aex.platform.repository.UserRepository;
import com.aex.platform.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author estar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Usuarios")
public class UserRestController {

  private final UserService service;
  @Autowired
  UserRepository dataRepository;

  @Autowired
  Utils utils;


  @GetMapping()
  public ResponseEntity<?> findAll() {
    List<UserAdapter> list =utils.userAdapterList( dataRepository.findAll());
    return ResponseEntity.status(HttpStatus.FOUND).body(list);
  }

  @GetMapping("/get-all-by-role/{role}")
  public ResponseEntity<?> findAllByRol(@PathVariable Role role) {

    Optional<List<User>> data = dataRepository.findAllByRole(role);
    if (data.isPresent()) {
      return ResponseEntity.ok(data.get());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{id}")
  public  ResponseEntity<?> findById(@PathVariable long id) {
    User save = dataRepository.findById(id).get();
    return ResponseEntity.status(HttpStatus.FOUND).body(new UserAdapter(save));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> put(@PathVariable long id, @RequestBody User input) {
    User save = dataRepository.save(input);
    return ResponseEntity.ok(save);
  }

  @PostMapping
  public ResponseEntity<?> post(@Valid @RequestBody User input, BindingResult result) {
    if (result.hasErrors()) {
      return validar(result);
    }
    if (dataRepository.findByEmail(input.getEmail()).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "El Correo electrónico ya esta registrado."));
    }
    if (dataRepository.findByNickname(input.getNickname()).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "El Nickname ya esta registrado."));
    }
    if (dataRepository.findByDocumentTypeAndDocumentNumber(input.getDocumentType(), input.getDocumentNumber()).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap("error", "El Documento ya está registrado."));
    }
    input.setFullName(input.getFirstName() + " " + input.getSecondName() + " " + input.getLastName() + " " + input.getSurname());
    //input.setCreatedAt( LocalDateTime.now(ZoneId.of("America/Bogota")));
    //input.setUpdatedAt( LocalDateTime.now(ZoneId.of("America/Bogota")));

    AuthenticationResponse token = service.register(input);

    if (input.getRole() == Role.CLIENT) {
      return ResponseEntity.status(HttpStatus.CREATED).body(dataRepository.findByEmail(input.getEmail()));
    }

    return ResponseEntity.ok(token);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable long id) {
    Optional<User> findById = dataRepository.findById(id);
    if (findById.get() != null) {
      dataRepository.delete(findById.get());
    }
    return ResponseEntity.ok().build();
  }

  private ResponseEntity<Map<String, String>> validar(BindingResult result) {
    Map<String, String> errores = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      errores.put(err.getField(), err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errores);
  }
}
