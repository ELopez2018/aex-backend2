package com.aex.platform.entities;

import com.aex.platform.entities.dtos.UserAdapter;
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

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "coordinate")
  private String coordinate;

  @Column(name = "address")
  private String address;

  @Column(name = "country")
  private String country;

  @Column(name = "state")
  private String state;

  @Column(name = "city")
  private String city;

  @Column(name = "parish")
  private String parish;

  @Column(name = "municipality")
  private String municipality;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "neighborhood")
  private String neighborhood;

  public User getUser() {
    return new UserAdapter(user);
  }

}