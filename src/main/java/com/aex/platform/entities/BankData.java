/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author estar
 */
@Data
@Entity
@Table(name = "bank_data")
public class BankData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id; 
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "bank_id")    
    private Bank bank;
    
    @Column(name ="accountNumber")
    private String accountNumber;
    
    @Column(name ="type")    
    private String type;

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "updated_at")
    public LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;
    
}
