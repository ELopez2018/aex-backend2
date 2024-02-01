package com.aex.platform.entities;

import lombok.Data;

@Data
public class TransactionTodo {
    private Long id;
    private Long transactionType;
    private String bank;
    private String correspondent;
    private String Benficiary;
    private String CellPhone;
    private Double value;
    private Long Status;
}
