/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.afrac.serviceorders.repository;

import com.afrac.serviceorders.entities.ServiceOrder;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author estar
 */
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
  Optional<List<ServiceOrder>> findByTechnicalId(long id);
  Optional<List<ServiceOrder>> findByVehicleId(long id);
}