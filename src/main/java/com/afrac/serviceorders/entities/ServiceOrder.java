/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.afrac.serviceorders.entities;


import com.afrac.serviceorders.entities.dtos.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

/**
 *
 * @author estar
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_orders")
public class ServiceOrder extends EntityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull(message = "El campo Motivo de entrada no debe estar vacio.")
    @Column(name = "title")
    private String title;


    @Column(name = "entry_date")
    private String entryDate;

    @NotNull(message = "El campo Fecha de Entrada no debe estar vacio.")
    @Column(name = "entry_time")
    private String entryTime;

    @Column(name = "departure_date")
    private String departureDate;

    @Column(name = "conclusions", length = 3000)
    private String conclusions;

    @Column(name = "fault_description", length = 3000)
    private String faultDescription;


    @Column(name = "mileage")
    private Long mileage;

    @Column(name = "fuel")
    private Double fuel;

    @Column(name = "status")
    private String status;

    @Column(name = "warranty")
    private boolean warranty=false;
    @NotNull(message = "El campo Propietario de Entrada no debe estar vacio.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

    @NotNull(message = "El campo Chofer no debe estar vacio.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private User driver;

    @NotNull(message = "El campo Vehiculo no debe estar vacio.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technical_id", nullable = true)
    private User technical;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "service_order_vehicle_reception",
        joinColumns = {@JoinColumn(name = "service_order_id")},
        inverseJoinColumns = {@JoinColumn(name = "vehicle_reception_id")}
    )
    private List<VehicleReception> vehicleReception;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "service_order_authorizations",
        joinColumns = {@JoinColumn(name = "service_order_id")},
        inverseJoinColumns = {@JoinColumn(name = "authorization_id")}
    )
    private List<Authorizations> authorizations;

}
