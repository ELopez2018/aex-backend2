/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "reference", unique = true)
    private  String reference;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "cashier_id", nullable = true)
    private User cashier;

    @ManyToOne
    @JoinColumn(name = "correspondent_id")
    private Correspondent correspondent;

    @ManyToOne
    @JoinColumn(name = "issuing_bank_id")
    private BankData issuingBank;

    @ManyToOne
    @JoinColumn(name = "receiving_bank_id")
    private BankData receivingBank;

    @Column(name = "status",nullable = true)
    private Long status;

    @Column(name = "amount_sent")
    private Double amountSent;

    @Column(name = "date_sent")
    private Long dateSend;

    @Column(name = "date_Receive")
    private Long dateReceive;

    @Column(name = "currency_from")
    private String currencyFrom;

    @Column(name = "currency_to")
    private String currencyTo;

    @Column(name = "amount_received")
    private Double amountReceived;
    
    @Column(name = "images")
    private String images;

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "updated_at")
    public LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;

    @Column(name = "observations", length = 1000)
    public String observations;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "transaction_id")
    private List<Voucher> voucher;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "transaction")
//    private List<Voucher> voucherList;
}
