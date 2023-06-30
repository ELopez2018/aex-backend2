/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.microservicetransactions.entities;

import java.util.Currency;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "transactions")
@DynamicInsert
@DynamicUpdate
public class Transaction extends EnitityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "cashier_id")
    private User cashier;

    @OneToOne
    @JoinColumn(name = "issuing_bank_id")
    private BankData issuingBank;

    @OneToOne
    @JoinColumn(name = "receiving_bank_id")
    private BankData receivingBank;

    @OneToOne
    @JoinColumn(name = "correspondent_id")
    private Correspondent correspondent;

    @Column(name = "amount_sent")
    private Double amountSent;

    @Column(name = "amount_received")
    private Double amountReceived;
    
    @Column(name = "images")
    private String images;
    
    
}
