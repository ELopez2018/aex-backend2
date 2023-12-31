/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.aex.platform.controllers;

import com.aex.platform.entities.Status;
import com.aex.platform.repository.StatusRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author estar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
@Tag(name = "Status")
public class StatusRestController {
    @Autowired
    StatusRepository statusRepository;
    
    @GetMapping()
    public List<Status> list() {
        return statusRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Status get(@PathVariable String id) {
        return null;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Status input) {
        return null;
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Status input) {
        Status save = statusRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return null;
    }
    
}
