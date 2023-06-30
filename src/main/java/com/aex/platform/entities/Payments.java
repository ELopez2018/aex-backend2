/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aex.microservicetransactions.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author estar
 */
@Data
@Entity
@Table(name = "payments")
@DynamicInsert
@DynamicUpdate
public class Payments extends EnitityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "datePaid")
    private LocalDateTime datePaid = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "details")
    private String details;

}
