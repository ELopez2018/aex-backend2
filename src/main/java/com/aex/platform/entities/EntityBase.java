/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.entities;

import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.time.ZoneId;


/**
 *
 * @author estar
 */

public class EntityBase {

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("America/Bogota"));;
    @Column(name = "updated_at")
    public LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("America/Bogota"));;
    @Column(name = "deleted_at")
    public LocalDateTime deletedAt;
}
