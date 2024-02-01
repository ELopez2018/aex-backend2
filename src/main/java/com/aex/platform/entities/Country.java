package com.aex.platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "countries")
public class Country {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name ="code")
  private String code;

  @Column(name ="phone_code")
  private String phoneCode;

  @Column(name ="currency")
  private String currency;

  @Column(name ="name")
  private String name;

  @Column(name ="icon_flag")
  private String iconFlag;



}
