package com.afrac.serviceorders.entities.dtos;

import com.afrac.serviceorders.entities.Authorizations;
import com.afrac.serviceorders.entities.User;
import com.afrac.serviceorders.entities.Vehicle;
import com.afrac.serviceorders.entities.VehicleReception;
import com.afrac.serviceorders.entities.models.VehicleOption;
import lombok.Data;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ServiceOrderDTO {
  private String title;
  private String entryDate;
  private String entryTime;
  private String departureDate;
  private String conclusions;
  private String faultDescription;
  private List<Authorizations> authorizations;
  private List<VehicleReception> vehicleReception;
  private Long mileage;
  private Double fuel;
  private String status;
  private Long owner;
  private Long driver;
  private Long vehicle;
  private Long technical;
  private boolean warranty;

}
