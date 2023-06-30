package com.aex.platform.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "addresses")
public class Address {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name = "coordinate")
  private String coordinate;

  @Column(name = "country")
  private String country;

  @Column(name = "estate")
  private String estate;

  @Column(name = "city")
  private String city;

  @Column(name = "parish")
  private String parish;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "neighborhood")
  private String neighborhood;

}