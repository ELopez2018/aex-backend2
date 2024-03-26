/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.aex.platform.repository;

import com.aex.platform.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author estar
 */
public interface PaymentsRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT SUM(p.amount) FROM Payment p "
            + "WHERE 1=1 "
            + "AND p.user.id = :userId "
            + "AND p.currency.id = :currencyId "
            + "AND p.approved = 'aprobado' "
    )
    Double getPaymentsTotal(@Param("userId") Long userId, @Param("currencyId") Long currencyId );
}
