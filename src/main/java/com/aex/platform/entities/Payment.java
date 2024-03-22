/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aex.platform.entities;

import com.aex.platform.entities.dtos.UserAdapter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author estar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type")
    private String type;

    @Column(name = "code")
    private String code;

    @Column(name = "bank")
    private String bank;

    @Column(name = "file", length = 500)
    private String file;

    @Column(name = "datePaid")
    private LocalDateTime datePaid = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "details")
    private String details;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "approved")
    public String approved;

    @Column(name = "verified_by")
    public String verifiedBy;

    @Column(name = "currency")
    public String currency;

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "updated_at")
    public LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("America/Bogota"));

    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;

    public User getUser() {
        if(user == null){
            return null;
        }
        return new UserAdapter(user);
    }

}
