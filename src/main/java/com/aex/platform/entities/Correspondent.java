/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aex.microservicetransactions.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author estar
 */
@Data
@Entity
@Table(name = "correspondents")
@DynamicInsert
@DynamicUpdate
public class Correspondent extends EnitityBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "nit")
    private String nit;

    @Column(name = "tradename")
    private String tradename;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "advertising_images")
    private String advertisingImages;
}
