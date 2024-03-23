package com.aex.platform.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name ="code")
    private String code;

    @Column(name ="description")
    private String description;

    @Column(name ="observations" , length = 2000)
    private String observations;

    @Column(name ="active")
    private Boolean active;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL)
    private List<Payment> payments;

}
