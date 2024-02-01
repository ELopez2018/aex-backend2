package com.aex.platform.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "states")
public class State {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;

  @Column(name ="code")
  private String code;

  @Column(name ="phone_code")
  private String phoneCode;


  @Column(name ="name")
  private String name;

}
