/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.auth.AuthenticationResponse;
import com.aex.platform.common.Constants;
import com.aex.platform.common.Utils;
import com.aex.platform.entities.Role;
import com.aex.platform.entities.Transaction;
import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.UserAdapter;
import com.aex.platform.repository.TransactionsRepository;
import com.aex.platform.repository.UserRepository;
import com.aex.platform.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Usuarios")
public class UserRestController {

    private final ApiService apiService;
    private final UserService service;
    @Autowired
    UserRepository dataRepository;

    @Autowired
    Utils utils;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    WebSocketService chat;

    @Autowired
    BalanceService balanceService;

    @Autowired
    UserService userService;

    @Autowired
    NotificationsService notificationsService;

//    @GetMapping()
//    public ResponseEntity<?> findAll(Pageable pageable) {
//        log.info(Constants.BAR);
//        log.info("Peticion get a /users Recibida");
//        List<UserAdapter> list = utils.userAdapterList(userService.findAll(pageable));
//        return ResponseEntity.status(HttpStatus.OK).body(list);
//    }

    @GetMapping()
    public ResponseEntity<?> findAll(Pageable pageable) {
        List<UserAdapter> list = utils.userAdapterList(userService.findAll(pageable));
        return ResponseEntity.status(HttpStatus.OK).body(list);
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
    public ResponseEntity<?> findById(@PathVariable long id) {
        User save = dataRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(new UserAdapter(save));
    }


    @GetMapping("/transactions")
    public ResponseEntity<?> findByDocTypeDocNumber(
            @RequestParam String type,
            @RequestParam Long number
    ) {
        Optional<User> userOptional = dataRepository.findByDocumentTypeAndDocumentNumber(type, number);
        if (userOptional.isPresent()) {
            List<Transaction> giros = transactionsRepository.findAllByClientId(userOptional.get().getId());
            return ResponseEntity.status(HttpStatus.OK).body(giros);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El usuario no existe."));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findUserByDni(
            @RequestParam String type,
            @RequestParam Long number
    ) {
        Optional<User> userOptional = dataRepository.findByDocumentTypeAndDocumentNumber(type, number);
        if (userOptional.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El usuario no existe."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody User input) {
        User save = dataRepository.save(input);
        return ResponseEntity.ok(save);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User input, BindingResult result) {
        log.info(Constants.BAR);
        log.info("Recibiendo solicitud de creacion de usuario...");
        if (result.hasErrors()) {
            log.severe("Se encontraron errores" + result);
            return validar(result);
        }
        if (dataRepository.findByEmail(input.getEmail()).isPresent()) {
            log.severe("El Correo electrónico ya esta registrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El Correo electrónico ya esta registrado."));
        }
        if (dataRepository.findByNickname(input.getNickname()).isPresent()) {
            log.severe("El Nickname ya esta registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El Nickname ya esta registrado."));
        }
        if (dataRepository.findByDocumentTypeAndDocumentNumber(input.getDocumentType(), input.getDocumentNumber()).isPresent()) {
            log.severe("El Documento ya está registrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "El Documento ya está registrado."));
        }
        input.setFirstName(input.getFirstName().toUpperCase());
        input.setSecondName(input.getSecondName().toUpperCase());
        input.setLastName(input.getLastName().toUpperCase());
        input.setSurname(input.getSurname().toUpperCase());
        input.setFullName(input.getFirstName() + " " + input.getSecondName() + " " + input.getLastName() + " " + input.getSurname());
        input.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")).toString());
        input.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")).toString());

        AuthenticationResponse token = service.register(input);

        if (input.getRole() == Role.CLIENT) {
            log.info("Cliente creado.");
            return ResponseEntity.status(HttpStatus.CREATED).body(new UserAdapter(dataRepository.findByEmail(input.getEmail()).get()));
        }
        log.info("Cliente creado.");
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

    @GetMapping("/migration")
    private ResponseEntity<?> migrateUsers() {
        String apiUrl = "http://localhost:3000/users"; // Reemplaza con la URL real de la API
        String responseData = apiService.fetchDataFromApi(apiUrl);
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/migration/transactions")
    private ResponseEntity<?> migrateTransactions() {
        String apiUrl = "http://localhost:3000/users/transactions"; // Reemplaza con la URL real de la API
        String responseData = apiService.migrationTransaction(apiUrl);
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/migration/contactos")
    private ResponseEntity<?> migrateContacts() {
        System.out.println("llego");
        String apiUrl = "http://localhost:3000/users/contacts"; // Reemplaza con la URL real de la API
        String responseData = apiService.migrationContacts(apiUrl);
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/search/custom")
    public ResponseEntity<?> findUserCustom(@RequestParam String query, @RequestParam Long documentNumber, Pageable pageable) {
        Page<User> userOptional = dataRepository.findAllUserCustom(documentNumber, query, pageable);
        if (!userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(userOptional);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Sin resultados."));
        }
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @GetMapping("/getBalance")
    public ResponseEntity<?> getBalance(@RequestParam Long userId, @RequestParam Long currencyId) {
        log.info(Constants.BAR);
        log.info("Peticion get a /users/getBalance Recibida");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap("user", balanceService.getBalance(userId, currencyId)));

    }

    @GetMapping("/test")
    public ResponseEntity<?> emailTest() throws MessagingException {
        log.info(Constants.BAR);
        log.info("Peticion get a email/test Recibida");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap("user", "email enviado"));

    }

    @GetMapping("/test/new-giro")
    public ResponseEntity<?> emailTestNewGiro() throws MessagingException {
        log.info(Constants.BAR);
        log.info("Peticion get a email/test Recibida");
        User user = dataRepository.findById(141L).get();
        notificationsService.sendNotificationToBenficiaryTIProgress(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap("user", "email enviado"));

    }

}
