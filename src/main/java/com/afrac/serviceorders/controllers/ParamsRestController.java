/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.afrac.serviceorders.controllers;

import com.afrac.serviceorders.repository.AuthorizationsRepository;
import com.afrac.serviceorders.repository.VehicleReceptionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author estar
 */
@RestController
@Tag(name = "Parametros")
@RequestMapping("/parameters")
public class ParamsRestController {

    @Autowired
    VehicleReceptionRepository vehicleReceptionRepository;
    @Autowired
    AuthorizationsRepository authorizationsRepository;
    
    @GetMapping("/vehicle-reception")
    public  ResponseEntity<?> VehicleReceptionslist() {

        return ResponseEntity.status(HttpStatus.FOUND)
            .body( vehicleReceptionRepository.findAll());
    }

    @GetMapping("/authorizations")
    public  ResponseEntity<?> Authorizationslist() {

        return ResponseEntity.status(HttpStatus.FOUND)
            .body( authorizationsRepository.findAll());
    }
    

}
