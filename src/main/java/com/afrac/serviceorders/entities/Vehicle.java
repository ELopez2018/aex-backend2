/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.afrac.serviceorders.entities;

import com.afrac.serviceorders.entities.dtos.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author estar
 */
@Data
@Entity
@Table(name = "vehicles")
@DynamicInsert
@DynamicUpdate
public class Vehicle extends EntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "El campo Placa no debe estar vacio.")
    @Column(unique = true, name = "license_plate")
    private String licensePlate;

    @NotBlank(message = "El campo Modelo no debe estar vacio.")
    @Column(name = "model")
    private String model;

    @NotBlank(message = "El campo Marca no debe estar vacio.")
    @Column(name = "brand")
    private String brand;

    @NotNull(message = "El campo Año no debe estar vacio.")
    @Column(name = "year")
    private Integer year;

    @NotNull(message = "El campo Color no debe estar vacio.")
    @Column(name = "color")
    private String color;

    @NotNull(message = "El campo Cilindraje no debe estar vacio.")
    @Column(name = "cylinder_capacity")
    private Double cylinderCapacity;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehicle")
    private List<ServiceOrder> serviceOrders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private User driver;
}
