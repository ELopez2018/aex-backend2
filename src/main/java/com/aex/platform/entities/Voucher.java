package com.aex.platform.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "mobile_payment_id")
    private MobilePayment mobilePayment;

    @Column(name = "image")
    private String image;

    @ColumnDefault(value = "false")
    @Column(name = "main")
    private Boolean main;

    @Column(name = "observations", length = 500)
    private String observations;

    @Column(name = "created_at", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    public String createdAt;

    @Column(name = "updated_at", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    public String updatedAt;

    @Column(name = "deleted_at")
    public String deletedAt;

}
