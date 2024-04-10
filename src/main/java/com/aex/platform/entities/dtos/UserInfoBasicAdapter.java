package com.aex.platform.entities.dtos;

import com.aex.platform.entities.BankData;
import com.aex.platform.entities.Menu;
import com.aex.platform.entities.Role;

import java.util.List;
import java.util.Set;

public class UserInfoBasicAdapter {
    private Long id;
    private String nickname;
    private String fullName;
    private String image;
    private String firstName;
    private String secondName;
    private String lastName;

    public UserInfoBasicAdapter(Long id, String nickname, String fullName, String image, String firstName, String secondName, String lastName, String surname, String birthdate, String gender, Long documentNumber, String documentType, String cellPhone, String phone, Long currentCountry, Long currency, String email, String coordinate, Double maximumAmount, Double balance, Boolean postpaid, Role role, List<BankData> bankDataList, Set<Menu> menu) {
        this.id = id;
        this.nickname = nickname;
        this.fullName = fullName;
        this.image = image;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.cellPhone = cellPhone;
        this.phone = phone;
        this.currentCountry = currentCountry;
        this.currency = currency;
        this.email = email;
        this.coordinate = coordinate;
        this.maximumAmount = maximumAmount;
        this.balance = balance;
        this.postpaid = postpaid;
        this.role = role;
        this.bankDataList = bankDataList;
        this.menu = menu;
    }

    private String surname;
    private String birthdate;
    private String gender;
    private Long documentNumber;
    private String documentType;
    private String cellPhone;
    private String phone;
    private Long currentCountry;
    private Long currency;
    private String email;
    private String coordinate;
    private Double maximumAmount;
    private Double balance;
    private Boolean postpaid;
    private Role role;
    private List<BankData> bankDataList;
    private Set<Menu> menu;
}
