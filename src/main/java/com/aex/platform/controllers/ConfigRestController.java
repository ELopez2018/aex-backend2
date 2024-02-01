package com.aex.platform.controllers;

import com.aex.platform.entities.TrmSetting;
import com.aex.platform.repository.BankRepository;
import com.aex.platform.repository.TrmSettingsRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config")
@Tag(name = "Configuraciones")
public class ConfigRestController {
    @Autowired
    TrmSettingsRepository trmSettingsRepository;
    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<TrmSetting> trmSettings = trmSettingsRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(trmSettings);
    }
}
