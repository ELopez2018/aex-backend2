/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.afrac.serviceorders.controllers;

import com.afrac.serviceorders.entities.ServiceOrder;
import com.afrac.serviceorders.entities.User;
import com.afrac.serviceorders.entities.Vehicle;
import com.afrac.serviceorders.entities.dtos.ServiceOrderDTO;
import com.afrac.serviceorders.entities.dtos.UserDTO;
import com.afrac.serviceorders.entities.models.Conclusions;
import com.afrac.serviceorders.repository.ServiceOrderRepository;
import com.afrac.serviceorders.repository.UserRepository;
import com.afrac.serviceorders.repository.VehicleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/service-orders")
@Tag(name = "Ordenes de Servicio")
public class ServiceOrderRestController {

    @Autowired
    ServiceOrderRepository dataRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;
    @GetMapping()
    public List<ServiceOrder> findAll() {
        return dataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        Optional<ServiceOrder> o = dataRepository.findById(id);
        if (o.isPresent()) {
            return ResponseEntity.ok().body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceOrder> put(@PathVariable long id, @RequestBody ServiceOrder input) {
        ServiceOrder save = dataRepository.save(input);
        return ResponseEntity.ok(save);
    }

   @PostMapping("/")
    public ResponseEntity<?> post(@Valid @RequestBody ServiceOrderDTO input, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        ServiceOrder serviceOrder = new ServiceOrder();
        Optional<User> driver = userRepository.findById(input.getDriver());
        Optional<User> owner = userRepository.findById(input.getOwner());
        Optional<Vehicle> vehicle  = (vehicleRepository.findById(input.getVehicle()));

        serviceOrder.setEntryDate(input.getEntryDate());
        serviceOrder.setEntryTime(input.getEntryTime());
        serviceOrder.setDepartureDate(input.getDepartureDate());
        serviceOrder.setConclusions(input.getConclusions());
        serviceOrder.setFaultDescription(input.getFaultDescription());
        serviceOrder.setStatus(input.getStatus());
        serviceOrder.setAuthorizations(input.getAuthorizations());
        serviceOrder.setVehicleReception(input.getVehicleReception());
        serviceOrder.setMileage(input.getMileage());
        serviceOrder.setFuel(input.getFuel());
        serviceOrder.setTitle(input.getTitle());
        serviceOrder.setVehicle(vehicle.get());
        serviceOrder.setDriver(driver.get());
        serviceOrder.setOwner(owner.get());
        ServiceOrder save = dataRepository.save(serviceOrder);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceOrder> delete(@PathVariable long id) {
        Optional<ServiceOrder> findById = dataRepository.findById(id);
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
    @PatchMapping("/assign-owner/{serviceOrderId}")
    public ResponseEntity<?> assignOwner(@Valid @RequestBody User ownerUser, BindingResult result, @PathVariable Long serviceOrderId) {

        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<ServiceOrder> serviceOrderOp = dataRepository.findById(serviceOrderId);
        Optional<User> techOptional = userRepository.findById(ownerUser.getId());
        System.out.println("step1");
        if (!serviceOrderOp.isPresent() || !techOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", "Faltan datos"));
        }
        ServiceOrder serviceOrderDb = serviceOrderOp.get();

        serviceOrderDb.setOwner(techOptional.get());
        ServiceOrder save = dataRepository.save(serviceOrderDb);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save);
    }
    @PatchMapping("/assign-driver/{serviceOrderId}")
    public ResponseEntity<?> assignDriver(@Valid @RequestBody User driverUser, BindingResult result, @PathVariable Long serviceOrderId) {

        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<ServiceOrder> serviceOrderOp = dataRepository.findById(serviceOrderId);
        Optional<User> techOptional = userRepository.findById(driverUser.getId());
        System.out.println("step1");
        if (!serviceOrderOp.isPresent() || !techOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", "Faltan datos"));
        }
        ServiceOrder serviceOrderDb = serviceOrderOp.get();

        serviceOrderDb.setDriver(techOptional.get());
        ServiceOrder save = dataRepository.save(serviceOrderDb);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save);
    }

    @PatchMapping("/assign-technical/{serviceOrderId}")
    public ResponseEntity<?> assignTechnical(@Valid @RequestBody User techUser, BindingResult result, @PathVariable Long serviceOrderId) {

        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<ServiceOrder> serviceOrderOp = dataRepository.findById(serviceOrderId);
        Optional<User> techOptional = userRepository.findById(techUser.getId());
        System.out.println("step1");
        if (!serviceOrderOp.isPresent() || !techOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Faltan datos"));
        }
        System.out.println("step2");
        ServiceOrder serviceOrderDb = serviceOrderOp.get();

        serviceOrderDb.setTechnical(techOptional.get());
        ServiceOrder save = dataRepository.save(serviceOrderDb);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save);
    }
    @PatchMapping("/close-order/{serviceOrderId}")
    public ResponseEntity<?>  addConclusions(@Valid @RequestBody Conclusions data, BindingResult result, @PathVariable Long serviceOrderId) {

        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<ServiceOrder> serviceOrderOp = dataRepository.findById(serviceOrderId);
         if (!serviceOrderOp.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Faltan datos"));
        }
        ServiceOrder serviceOrderDb = serviceOrderOp.get();

        serviceOrderDb.setStatus("CERRADA");
        serviceOrderDb.setConclusions(data.conclusions);
        serviceOrderDb.setDepartureDate(data.departureDate);
        ServiceOrder save = dataRepository.save(serviceOrderDb);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save);
    }
    @GetMapping("/technical/{id}")
    public ResponseEntity<?> findByTechnicalId(@PathVariable long id) {
        Optional<List<ServiceOrder>> o = dataRepository.findByTechnicalId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok().body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/license-plate/all/{id}")
    public ResponseEntity<?> findByLicensePlate(@PathVariable long id) {
        Optional<List<ServiceOrder>> o = dataRepository.findByVehicleId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok().body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PatchMapping("/change-state/{serviceOrderId}")
    public ResponseEntity<?>  changeState(@Valid @RequestBody String state, BindingResult result, @PathVariable Long serviceOrderId) {
        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<ServiceOrder> serviceOrderOp = dataRepository.findById(serviceOrderId);

        if (!serviceOrderOp.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", "Faltan datos"));
        }
        ServiceOrder serviceOrderDb = serviceOrderOp.get();

        serviceOrderDb.setStatus(state);
        ServiceOrder save = dataRepository.save(serviceOrderDb);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save);
    }
}
