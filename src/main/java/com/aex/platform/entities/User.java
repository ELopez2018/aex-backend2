/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aex.platform.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    //@JsonIgnore
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

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "document_number", unique = true)
    @NotNull(message = "EL Numero de Documento es obligatorio")
    private Long documentNumber;

    @Column(name = "document_type")
    @NotNull(message = "EL tipo de Documento es obligatorio")
    private String documentType;

    @Column(name = "cellPhone")
    private String cellPhone;

    @Column(name = "phone")
    private String phone;

    @Column(name = "current_country_id", nullable = true)
    private Long currentCountry;

    @Column(name = "currency_id", nullable = true)
    private Long currency;

    @Column(name = "email")
    @NotNull(message = "EL Email es obligatorio")
    private String email;

    @Column(name = "confirmed_email")
    private String confirmedEmail;

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
    public String createdAt;

    @Column(name = "updated_at")
    public String updatedAt;

    @Column(name = "deleted_at")
    public String deletedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<BankData> bankDataList;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "users_menus",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<Menu> menu;

}
