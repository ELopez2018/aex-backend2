package com.aex.platform.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOld {
  private float Id;
  private float Id_User;
  private String name;
  private String identification;
  private String typeIdentification;
  private String email;
  private String indicative;
  private String phone;
  private String account_bank;
  private String account_type;
  private String account_number;
  private String birthday;
  private String whatsapp;
  private String state;
  private float register;
}
