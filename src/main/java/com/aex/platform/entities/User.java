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

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "password")
  private String password;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "secondName")
  private String secondName;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "surname")
  private String surname;

  @Column(name = "documentNumber")
  @NotNull(message = "EL Numerom de Documento es obligatorio")
  private Long documentNumber;

  @Column(name = "documenType")
  private String documenType;

  @Column(name = "cellPhone")
  private String cellPhone;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "coordinate")
  private String coordinate;

  @Column(name = "maximum_amount")
  private Double maximumAmount;

  @Column(name = "balance")
  private Double balance;

  @Column(name = "postpaid")
  private Boolean postpaid

    @Enumerated(EnumType.STRING)
    private Role role;



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
