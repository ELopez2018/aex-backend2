package com.afrac.serviceorders.entities.dtos;

import com.afrac.serviceorders.entities.Role;
import com.afrac.serviceorders.entities.ServiceOrder;
import com.afrac.serviceorders.entities.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
@Data
public class UserDTO {

  private String nickname;


  private String password;


  private String names;


  private String lastNames;


  private Long documentNumber;


  private String documentType;


  private Long cellPhone;


  private Long phone;

  private String email;


  private Role role;


  private List<Vehicle> vehicleOwners;


  private List<Vehicle> vehicleDrivers;


  private List<ServiceOrder> serviceOrdersOwner;


  private List<ServiceOrder> serviceOrdersDriver;

}
