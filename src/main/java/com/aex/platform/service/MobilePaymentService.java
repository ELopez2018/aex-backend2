package com.aex.platform.service;

import com.aex.platform.entities.MobilePayment;
import com.aex.platform.entities.dtos.MobilePaymentDto;
import com.aex.platform.repository.MobilePaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MobilePaymentService {
    @Autowired
    private TransactionService transactionService;

    private final MobilePaymentRepository mobilePaymentRepository;

    public List<MobilePayment> create(List<MobilePayment> data) {
        ModelMapper modelMapper = new ModelMapper();
        List<MobilePayment> mobilePayments = data.stream()
                .map(dto -> modelMapper.map(dto, MobilePayment.class))
                .collect(Collectors.toList());
        List<MobilePayment> resp = mobilePaymentRepository.saveAllAndFlush(mobilePayments);
        if (!resp.isEmpty()) {
            transactionService.updateWebsocketTransactionsTodo(List.of(1L));
        }
        return resp;
    }
    public Page<MobilePayment> getAll(Pageable page) {
        return mobilePaymentRepository.findAll(page);
    }

}
