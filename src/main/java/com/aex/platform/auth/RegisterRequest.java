package com.aex.platform.auth;


import com.aex.platform.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String documentType;
  private String documentNumber;
  private String firstName;
  private String lastName;
  private String address;
  private String cellPhone;
  private String email;
  private String phone;
  private String password;
  private Role role;


}
