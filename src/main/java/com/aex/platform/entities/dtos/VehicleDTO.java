package com.afrac.serviceorders.entities.dtos;

import lombok.Data;

@Data
public class VehicleDTO {

    protected Long id;

    private String licensePlate;

    private String model;

    private String brand;

    private Integer year;

    private String color;

    private Double cylinderCapacity;

    private Long owner;
    private Long driver;
}
