package com.aex.platform.controllers;

import com.aex.platform.entities.*;
import com.aex.platform.repository.CountryRepository;
import com.aex.platform.repository.MunicipalityRepository;
import com.aex.platform.repository.ParishRepository;
import com.aex.platform.repository.StateRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
@Tag(name = "Paises")
public class CountryRestController {

  @Autowired
  CountryRepository dataRepository;

  @Autowired
  StateRepository stateRepository;

  @Autowired
  MunicipalityRepository municipalityRepository;

  @Autowired
  ParishRepository parishRepository;

  @GetMapping()
  public ResponseEntity<?> list() {
    try {
      List <Country> countries = dataRepository.findAll();
      return ResponseEntity.status(HttpStatus.OK).body(countries);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
  }
  @GetMapping("/parish")
  public ResponseEntity<?> listParish() {
    try {
      List <Parish> countries = parishRepository.findAll();
      return ResponseEntity.status(HttpStatus.OK).body(countries);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
  }
  @GetMapping("/states/{countryId}")
  public ResponseEntity<?> getAllStates(@PathVariable Long countryId) {
    try {
      List <State> countries = stateRepository.findAllByCountryId(countryId);
      return ResponseEntity.status(HttpStatus.OK).body(countries);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
  }

  @GetMapping("/states/municipality/{stateId}")
  public ResponseEntity<?> getAllMunicipality(@PathVariable Long stateId) {
    try {
      List <Municipality> countries = municipalityRepository.findAllByStateId(stateId);
      return ResponseEntity.status(HttpStatus.OK).body(countries);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
  }
  @GetMapping("/states/municipality/parish/{municipalityId}")
  public ResponseEntity<?> getAllParishByMunicipality(@PathVariable Long municipalityId) {
    try {
      List <Parish> countries = parishRepository.findAllByMunicipalityId(municipalityId);
      return ResponseEntity.status(HttpStatus.OK).body(countries);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
  }

}
