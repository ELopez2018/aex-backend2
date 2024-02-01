package com.aex.platform.entities.dtos;

import com.aex.platform.entities.BankData;
import com.aex.platform.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  protected Long id;
  private String nickname;
  private String fullName;
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
  private List<BankData> bankData;
}
