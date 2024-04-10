package com.aex.platform.entities;

import lombok.Data;

import java.util.List;

@Data
public class TransactionTodo {
    private Long id;
    private Long transactionType;
    private String bank;
    private String bankAccount;
    private String correspondent;
    private String benficiary;
    private String cellPhone;
    private Double value;
    private Long status;
    private String type;
    private Long dni;
    private Long cashier;
    private String cashierName;
    private List<Voucher> voucher;
}
