/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.aex.platform.repository;

import com.aex.platform.entities.Payments;
import com.aex.platform.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author estar
 */
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    @Query("SELECT SUM(p.amount) FROM Payments p "
            + "WHERE 1=1 "
            + "AND p.user.id = :userId "
            + "AND p.approved is not null"
    )
    Double getPaymentsTotal(@Param("userId") Long userId);
}
