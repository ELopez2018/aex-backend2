/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aex.platform.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author estar
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "fullName")
  private String fullName;

  @Column(name = "image")
  private String image;

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

  @Column(name = "document_number")
  @NotNull(message = "EL Numero de Documento es obligatorio")
  private Long documentNumber;

  @Column(name = "document_type")
  @NotNull(message = "EL tipo de Documento es obligatorio")
  private String documentType;

  @Column(name = "cellPhone")
  private String cellPhone;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  @NotNull(message = "EL Email  es obligatorio")
  private String email;

  @Column(name = "coordinate")
  private String coordinate;

  @Column(name = "maximum_amount")
  private Double maximumAmount;

  @Column(name = "balance")
  private Double balance;

  @Column(name = "postpaid")
  private Boolean postpaid;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "created_at")
  public LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Bogota"));
  ;
  @Column(name = "updated_at")
  public LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("America/Bogota"));
  ;
  @Column(name = "deleted_at")
  public LocalDateTime deletedAt;
}
