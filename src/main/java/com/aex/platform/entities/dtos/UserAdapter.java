package com.aex.platform.entities.dtos;

import com.aex.platform.entities.BankData;
import com.aex.platform.entities.Role;
import com.aex.platform.entities.User;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
public class UserAdapter extends User {
  private final User user;


  @Override
  public Long getId() {
    return user.getId();
  }

  @Override
  public String getNickname() {
    return user.getNickname();
  }

  @Override
  public String getFullName() {
    return user.getFullName();
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getFirstName() {
    return user.getFirstName();
  }

  @Override
  public String getSecondName() {
    return user.getSecondName();
  }

  @Override
  public String getLastName() {
    return user.getLastName();
  }

  @Override
  public String getSurname() {
    return user.getSurname();
  }

  @Override
  public Long getDocumentNumber() {
    return user.getDocumentNumber();
  }

  @Override
  public String getDocumentType() {
    return user.getDocumentType();
  }

  @Override
  public String getCellPhone() {
    return user.getCellPhone();
  }

  @Override
  public String getPhone() {
    return user.getPhone();
  }

  @Override
  public String getEmail() {
    return user.getEmail();
  }

  @Override
  public String getCoordinate() {
    return user.getCoordinate();
  }

  @Override
  public Double getMaximumAmount() {
    return user.getMaximumAmount();
  }

  @Override
  public Double getBalance() {
    return user.getBalance();
  }


  @Override
  public Role getRole() {
    return user.getRole();
  }

  @Override
  public Boolean getPostpaid() {
    return user.getPostpaid();
  }

  @Override
  public String getCreatedAt() {
    return user.getCreatedAt();
  }

  @Override
  public String getUpdatedAt() {
    return user.getCreatedAt();
  }

  @Override
  public String getDeletedAt() {
    return user.getDeletedAt();
  }

  @Override
  public List<BankData> getBankDataList() {
    return user.getBankDataList();
  }

  @Override
  public Long getCurrentCountry() {
    return user.getCurrentCountry();
  }

  @Override
  public Long getCurrency() {
    return user.getCurrency();
  }
}
