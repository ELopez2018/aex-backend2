/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;


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

    @OneToOne
    @JoinColumn(name = "client_id")
    private User client;

    @OneToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @OneToOne
    @JoinColumn(name = "cashier_id", nullable = true)
    private User cashier;

    @OneToOne
    @JoinColumn(name = "correspondent_id")
    private Correspondent correspondent;

    @OneToOne
    @JoinColumn(name = "issuing_bank_id")
    private BankData issuingBank;

    @OneToOne
    @JoinColumn(name = "receiving_bank_id")
    private BankData receivingBank;

    @Column(name = "status",nullable = true)
    private String status;

    @Column(name = "amount_sent")
    private Double amountSent;

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
    
    
}
