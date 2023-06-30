/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.aex.microservicetransactions.repossitory;

import com.aex.microservicetransactions.entities.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author estar
 */
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    
}
