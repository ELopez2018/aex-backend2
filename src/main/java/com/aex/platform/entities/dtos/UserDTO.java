package com.aex.platform.entities.dtos;

import com.aex.platform.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
  protected Long id;

  public Long getId() {
    return id;
  }

  public String getNickname() {
    return nickname;
  }

  public String getFullName() {
    return fullName;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getSurname() {
    return surname;
  }

  public Long getDocumentNumber() {
    return documentNumber;
  }

  public String getDocumentType() {
    return documentType;
  }

  public String getCellPhone() {
    return cellPhone;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public String getCoordinate() {
    return coordinate;
  }

  public Double getMaximumAmount() {
    return maximumAmount;
  }

  public Double getBalance() {
    return balance;
  }

  public Boolean getPostpaid() {
    return postpaid;
  }

  private String nickname;
  private String fullName;
  private String password;
  private String firstName;
  private String secondName;
  private String lastName;
  private String surname;
  private Long documentNumber;
  private String documentType;
  private String cellPhone;
  private String phone;
  private String email;
  private String coordinate;
  private Double maximumAmount;
  private Double balance;
  private Boolean postpaid;
}
