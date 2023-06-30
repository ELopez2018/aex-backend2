/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.microservicetransactions.repossitory;

import com.aex.microservicetransactions.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author estar
 */
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
    
    
   
}
