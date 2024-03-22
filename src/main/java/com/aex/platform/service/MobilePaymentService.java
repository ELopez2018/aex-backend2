package com.aex.platform.service;

import com.aex.platform.entities.MobilePayment;
import com.aex.platform.entities.Transaction;
import com.aex.platform.entities.TransactionTodo;
import com.aex.platform.entities.User;
import com.aex.platform.entities.dtos.MobilePaymentDto;
import com.aex.platform.repository.MobilePaymentRepository;
import com.aex.platform.repository.TransactionsRepository;
import com.aex.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@Service
@RequiredArgsConstructor
public class MobilePaymentService {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private UserRepository userRepository;

    private final MobilePaymentRepository mobilePaymentRepository;

    public List<MobilePayment> create(List<MobilePayment> data) {
        log.info("Creando pagos moviles");
        List<MobilePayment> resp = mobilePaymentRepository.saveAll(data);

        if (!resp.isEmpty()) {
            log.info("Enviado info al Socket..");
            transactionService.updateWebsocketTransactionsTodo(List.of(1L));
        }
        return resp;
    }

    public Page<MobilePayment> getAll(Pageable page) {
        return mobilePaymentRepository.findAll(page);
    }

    public ResponseEntity<?> updateStatusTransaction(TransactionTodo transactionTodo) {
        log.info("Validando tipo de transaccion");
        if (transactionTodo.getTransactionType().equals(1L)) {
            log.info("Transaccion tipo: GIRO");
            log.info("Buscando GIRO: " + transactionTodo.getId());
            Optional<Transaction> transactionOptional = transactionsRepository.findById(transactionTodo.getId());
            if (transactionOptional.isPresent()) {
                log.info("GIRO encontrado");
                Transaction transaction = transactionOptional.get();
                log.info("Actualizando estado");
                transaction.setStatus(transactionTodo.getStatus());
                if (transactionTodo.getCashier() != null) {
                    log.info("Obteniendo Cajero");
                    Optional<User> cashierOptional = userRepository.findById(transactionTodo.getCashier());

                    if (cashierOptional.isPresent()) {
                        log.info("Cajero" + cashierOptional.get().getFullName());
                        log.info("Asignando Cajero");
                        transaction.setCashier(cashierOptional.get());
                    }
                }

                transaction = transactionService.save(transaction);
                log.info("Enviado informacion al SOCKET");
                transactionService.updateWebsocketTransactionsTodo(List.of(transactionTodo.getStatus()));
                return ResponseEntity.ok().body(transactionService.save(transaction));
            }
            log.info("GIRO " + transactionTodo.getId() + "No encontrado");
        } else if (transactionTodo.getTransactionType().equals(2L)) {
            log.info("Transaccion tipo: PAGO MOVIL");
            log.info("Buscando PAGO MOVIL: " + transactionTodo.getId());
            Optional<MobilePayment> mobilePaymentOptional = mobilePaymentRepository.findById(transactionTodo.getId());
            if (mobilePaymentOptional.isPresent()) {
                log.info("PAGO MOVIL encontrado");
                MobilePayment mobilePayment = mobilePaymentOptional.get();

                log.info("Actualizando estado");
                mobilePayment.setStatus(transactionTodo.getStatus());
                if (transactionTodo.getCashier() != null) {
                    Optional<User> cashierOptional = userRepository.findById(transactionTodo.getCashier());
                    if (cashierOptional.isPresent()) {
                        log.info("Cajero" + cashierOptional.get().getFullName());
                        log.info("Asignando Cajero");
                        mobilePayment.setCashier(cashierOptional.get());
                    }
                }
                mobilePayment = mobilePaymentRepository.save(mobilePayment);
                log.info("Enviado informacion al SOCKET");
                transactionService.updateWebsocketTransactionsTodo(List.of(transactionTodo.getStatus()));
                return ResponseEntity.ok().body(mobilePayment);
            }
            log.info("PAGO MOVIL " + transactionTodo.getId() + "No encontrado");
        }
        transactionService.updateWebsocketTransactionsTodo(List.of(6L));
        return ResponseEntity.badRequest().body("No se reconoce el tipo de transaccion.");
    }

}
