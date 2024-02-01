package com.aex.platform.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "mobile_payments")
public class MobilePayment extends EntityBase {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name ="document_type")
    private String documentType;

    @Column(name ="document_number")
    private String documentNumber;

    @Column(name ="cellPhone_number")
    private String cellPhoneNumber;

    @Column(name ="trm", nullable = true)
    private Integer trm;

    @Column(name ="value_receive")
    private Double valueReceive;

    @Column(name ="value_to_send")
    private Double valueToSend;

    @Column(name = "status",nullable = true)
    private Long status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "cashier_id", nullable = true)
    private User cashier;

    @ManyToOne
    @JoinColumn(name = "correspondent_id")
    private Correspondent correspondent;

    @JsonManagedReference
    @OneToMany(mappedBy = "mobilePayment")
    private List<Voucher> voucherList;

}
