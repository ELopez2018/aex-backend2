/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.afrac.serviceorders.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author estar
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends EntityBase implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "password")
    private String password;

   @NotBlank(message = "El campo Nombres no debe estar vacio.")
    @Column(name = "names")
    private String names;

    @NotBlank(message = "El campo Apellidos no debe estar vacio.")
    @Column(name = "lastNames")
    private String lastNames;

    @Column(name = "documentNumber")
    private Long documentNumber;

    @Column(name = "documentType")
    private String documentType;

    @Column(name = "cellPhone")
    private Long cellPhone;

    @Column(name = "phone")
    private Long phone;

    @NotBlank(message = "El campo Placa no debe estar vacio.")
    @Column(unique = true, name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Vehicle> vehicleOwners;

    @JsonIgnore
    @OneToMany(mappedBy = "driver")
    private List<Vehicle> vehicleDrivers;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private List<ServiceOrder> serviceOrdersOwner;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "driver")
  private List<ServiceOrder> serviceOrdersDriver;


  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
 @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }
}
