package com.aex.platform.service;

import com.aex.platform.entities.MobilePayment;
import com.aex.platform.entities.Transaction;
import com.aex.platform.entities.Voucher;
import com.aex.platform.entities.dtos.VoucherDto;
import com.aex.platform.interfaces.VoucherInterface;
import com.aex.platform.repository.MobilePaymentRepository;
import com.aex.platform.repository.TransactionsRepository;
import com.aex.platform.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log
@Service
@RequiredArgsConstructor
public class VoucherService implements VoucherInterface {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private MobilePaymentRepository mobilePaymentRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> saveAll(List<Voucher> voucher) {
        return voucherRepository.saveAll(voucher);
    }

    @Override
    public Optional<List<Voucher>> findByMobilePaymentId(Long mobilPayId) {
        return voucherRepository.findAllByMobilePaymentId(mobilPayId);
    }

    @Override
    public Optional<List<Voucher>> findTransactionId(Long transactionId) {
        return voucherRepository.findAllByTransactionId(transactionId);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public boolean deleteById(Long Id) {
        try {
            voucherRepository.deleteById(Id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean deleteAll(List<Long> Ids) {
        return false;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Voucher saveVoucher(VoucherDto voucherDto) {
        if (voucherDto.getTransactionType() == 1L) {
            log.info("El vaucher es de un GIRO");
            Optional<Transaction> transactionOptional = transactionsRepository.findById(voucherDto.getTransactionId());
            Voucher voucher = new Voucher();
            voucher.setTransaction(transactionOptional.get());
            voucher.setMain(voucherDto.getMain());
            voucher.setObservations(voucherDto.getObservations());
            try {
                voucher.setImage(fileStorageService.save("/vouchers", voucherDto.getImage()[0], transactionOptional.get().getClient().getDocumentNumber().toString(), voucherDto.getTransactionId()).toAbsolutePath().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("Guardando el voucher del GIRO");
            return voucherRepository.save(voucher);
        } else if (voucherDto.getTransactionType() == 2L) {
            log.info("El vaucher es de un PAGO MOVIL");
            Optional<MobilePayment> mobilePaymentOptional = mobilePaymentRepository.findById(voucherDto.getTransactionId());
            Voucher voucher = new Voucher();
            voucher.setMobilePayment(mobilePaymentOptional.get());
            voucher.setMain(voucherDto.getMain());
            voucher.setObservations(voucherDto.getObservations());
            try {
                voucher.setImage(fileStorageService.save("/vouchers", voucherDto.getImage()[0], mobilePaymentOptional.get().getClient().getDocumentNumber().toString(), voucherDto.getTransactionId()).toAbsolutePath().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            log.info("Guardando el voucher del PAGO MOVIL");
            return voucherRepository.save(voucher);
        }
        log.info("Tipo invalido: " + voucherDto.getTransactionType());
        return null;
    }

   public boolean existsById(Long id){
       return voucherRepository.existsById(id);
   }
}
