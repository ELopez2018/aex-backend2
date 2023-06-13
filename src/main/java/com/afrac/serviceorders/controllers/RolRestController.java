/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.afrac.serviceorders.controllers;

import com.afrac.serviceorders.entities.Rol;
import com.afrac.serviceorders.entities.User;
import com.afrac.serviceorders.repository.RolRepository;
import com.afrac.serviceorders.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author estar
 */
@RestController
@RequestMapping("/rol")
@Tag(name = "Roles")
public class RolRestController {
    @Autowired
    RolRepository dataRepository;
    
   @GetMapping()
    public List<Rol> findAll() {
        return dataRepository.findAll();
    }

    @GetMapping("/{id}")
    public Rol findById(@PathVariable long id) {
        Rol save = dataRepository.findById(id).get();
        return save;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> put(@PathVariable long id, @RequestBody Rol input) {
        Rol save = dataRepository.save(input);
        return ResponseEntity.ok(save);
    }

    @PostMapping
    public ResponseEntity<Rol> post(@RequestBody Rol input) {
        Rol save = dataRepository.save(input);

        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Rol> delete(@PathVariable long id) {
        Optional<Rol> findById = dataRepository.findById(id);
        if (findById.get() != null) {
            dataRepository.delete(findById.get());
        }
        return ResponseEntity.ok().build();
    }
    
}
