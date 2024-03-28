package com.aex.platform.entities.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResumeDto {
    private Long currencyId;
    private String currencyCode;
    private String currencyDescrip;
    private double totaPaid;
    private double totalSpent;
    private double balance;

    public PaymentResumeDto(Long currencyId, String currencyCode, String currencyDescrip,  double totaPaid) {
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.currencyDescrip = currencyDescrip;
        this.totaPaid = totaPaid;
    }
}
