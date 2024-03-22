package com.aex.platform.controllers;

import com.aex.platform.common.Constants;
import com.aex.platform.entities.MobilePayment;
import com.aex.platform.entities.User;
import com.aex.platform.entities.Voucher;
import com.aex.platform.entities.dtos.UserAdapter;
import com.aex.platform.entities.dtos.VoucherDto;
import com.aex.platform.service.UserService;
import com.aex.platform.service.VoucherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/vouchers")
@Tag(name = "Comprobantes")
public class VoucherController {
    private final UserService userService;
    private final VoucherService voucherService;

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(voucherService.findAll());
    }

    @GetMapping("/mobile-payments")
    public ResponseEntity<?> findMobilePaymentById(@RequestParam Long mobilePaymentId) {
        return ResponseEntity.status(HttpStatus.OK).body(voucherService.findByMobilePaymentId(mobilePaymentId));
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> findTransactionId(@RequestParam Long transactionId) {
        return ResponseEntity.status(HttpStatus.OK).body(voucherService.findTransactionId(transactionId));
    }

    @PostMapping()
    public ResponseEntity<?> saveVoucher(
            @RequestBody MultipartFile[] files,
            @RequestParam("transactionType") Long transactionType,
            @RequestParam("transactionId") Long transactionId,
            @RequestParam("main") boolean main,
            @RequestParam("observations") String observations
    ) {
        VoucherDto voucherDto = new VoucherDto();
        voucherDto.setObservations(observations);
        voucherDto.setTransactionType(transactionType);
        voucherDto.setTransactionId(transactionId);
        voucherDto.setMain(main);
        voucherDto.setImage(files);
        log.info(Constants.BAR);
        log.info("Datos recibidos");
        if(files == null || files.length == 0){
            log.info("Datos no recibidos");
            return ResponseEntity.badRequest().body("No llego el archivo");
        }
        for(MultipartFile file : files) {
            log.info(file.getOriginalFilename());
        }
        return ResponseEntity.ok().body(voucherService.saveVoucher(voucherDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(voucherService.deleteById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Voucher voucher) {
        try {
            // Validar la entrada
            if (voucher == null) {
                return ResponseEntity.badRequest().body("El voucher no puede ser nulo");
            }

            // Verificar si el voucher con el ID proporcionado existe antes de actualizarlo
            if (!voucherService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            // Actualizar el voucher
            Voucher updatedVoucher = voucherService.save(voucher);

            // Devolver la respuesta con el voucher actualizado
            return ResponseEntity.ok(updatedVoucher);
        } catch (Exception e) {
            // Manejo de errores: puedes registrar el error o lanzar una excepción específica según sea necesario
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al actualizar el voucher.");
        }
    }
}
