/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.afrac.serviceorders.controllers;

import com.afrac.serviceorders.entities.ServiceOrder;
import com.afrac.serviceorders.entities.User;
import com.afrac.serviceorders.entities.Vehicle;
import com.afrac.serviceorders.entities.dtos.UserDTO;
import com.afrac.serviceorders.entities.dtos.VehicleDTO;
import com.afrac.serviceorders.repository.ServiceOrderRepository;
import com.afrac.serviceorders.repository.UserRepository;
import com.afrac.serviceorders.repository.VehicleRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;

/**
 *
 * @author estar
 */
@RestController
@RequestMapping("/vehicle")
@Tag(name = "Vehículos")
public class VehicleRestController {

    @Autowired
    VehicleRepository dataRepository;
    @Autowired
    ServiceOrderRepository serviceOrderRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public List<Vehicle> findAll() {
        return dataRepository.findAll();
    }

    @GetMapping("/{id}")
    public Vehicle findById(@PathVariable long id) {
        Vehicle save = dataRepository.findById(id).get();
        return save;
    }

    @GetMapping("/license-plate/{findByLicensePlate}")
    public ResponseEntity<?> findByIdlicensePlate(@PathVariable String findByLicensePlate) {
       Optional<Vehicle>  vehicleOptional = dataRepository.findByLicensePlate(findByLicensePlate);
       if(vehicleOptional.isPresent()) {
           return ResponseEntity.ok(vehicleOptional.get());
       }
       //Collections.singletonMap("error", "El Documento ya está registrado.")
       return  ResponseEntity.status(HttpStatus.NOT_FOUND)
           .body(Collections.singletonMap("error", "La placa no está registrada."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Vehicle input) {
        Optional<Vehicle> vehicleOptional = dataRepository.findById(id);
        if (vehicleOptional.isPresent()) {

            Vehicle VehicleDb = vehicleOptional.get();
            if (! input.getLicensePlate().equalsIgnoreCase(VehicleDb.getLicensePlate()) && dataRepository.findByLicensePlate(input.getLicensePlate()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("error", "La Placa ya esta registrada."));
            }
            VehicleDb.setBrand(input.getBrand());
            VehicleDb.setColor(input.getColor());
            VehicleDb.setCylinderCapacity(input.getCylinderCapacity());
            VehicleDb.setLicensePlate(input.getLicensePlate());
            VehicleDb.setModel(input.getModel());
            VehicleDb.setYear(input.getYear());
            Vehicle save = dataRepository.save(VehicleDb);
            return ResponseEntity.ok(save);

        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody VehicleDTO input, BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }
        if (dataRepository.findByLicensePlate(input.getLicensePlate()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "La Placa ya esta registrada."));
        }

        Optional<User> ownerOptional = userRepository.findById(input.getOwner());
        Optional<User> driverrOptional = userRepository.findById(input.getDriver());
        Vehicle vehicleDB = new Vehicle();

        vehicleDB.setBrand(input.getBrand());
        vehicleDB.setModel(input.getModel());
        vehicleDB.setColor(input.getColor());
        vehicleDB.setYear(input.getYear());
        vehicleDB.setLicensePlate(input.getLicensePlate());
        vehicleDB.setCylinderCapacity(input.getCylinderCapacity());
        vehicleDB.setOwner(ownerOptional.get());
        vehicleDB.setDriver(driverrOptional.get());
        Vehicle save = dataRepository.save(vehicleDB);

        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
   @PatchMapping("/assign-driver/{vehicleId}")
    public ResponseEntity<?> assignDriver(@Valid @RequestBody User driverUser, BindingResult result, @PathVariable Long vehicleId) {

        if (result.hasErrors()) {
            return validar(result);
        }
        //return ResponseEntity.status(HttpStatus.OK).body(driverUser);
        
        Optional<Vehicle> vehicleOp = dataRepository.findById(vehicleId);
        Optional<User> driverOptional = userRepository.findById(driverUser.getId());

        if (!vehicleOp.isPresent() || !driverOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Faltan datos"));
        }
        Vehicle vehicleDb = vehicleOp.get();
        
        vehicleDb.setDriver(driverOptional.get());
        Vehicle save = dataRepository.save(vehicleDb);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save);
    }
   @PatchMapping("/assign-owner/{vehicleId}")
    public ResponseEntity<?> assignOwner(@Valid @RequestBody User ownerUser, BindingResult result, @PathVariable Long vehicleId) {

        if (result.hasErrors()) {
            return validar(result);
        }
        
        Optional<Vehicle> vehicleOp = dataRepository.findById(vehicleId);
        Optional<User> ownerOptional = userRepository.findById(ownerUser.getId());

        if (!vehicleOp.isPresent() || !ownerOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Faltan datos"));
        }
        Vehicle vehicleDb = vehicleOp.get();
        
        vehicleDb.setOwner(ownerOptional.get());
        Vehicle save = dataRepository.save(vehicleDb);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(save);
    }
    @PostMapping("/history")
    public ResponseEntity<List<ServiceOrder>> post(@RequestBody long id) {
        List<ServiceOrder> save = serviceOrderRepository.findAll();
        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vehicle> delete(@PathVariable long id) {
        Optional<Vehicle> findById = dataRepository.findById(id);
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
