/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.afrac.serviceorders.repository;

import com.afrac.serviceorders.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author estar
 */
public interface RolRepository extends JpaRepository<Rol, Long> {
    
}
