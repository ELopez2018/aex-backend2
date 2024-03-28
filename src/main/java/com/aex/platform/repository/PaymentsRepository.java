/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.aex.platform.repository;

import com.aex.platform.entities.Currency;
import com.aex.platform.entities.Payment;
import com.aex.platform.entities.dtos.PaymentResumeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author estar
 */
public interface PaymentsRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT SUM(p.amount) FROM Payment p "
            + "WHERE 1=1 "
            + "AND p.user.id = :userId "
            + "AND p.currency.id = :currencyId "
            + "AND p.status = 'aprobado' "
    )
    Double getPaymentsTotal(@Param("userId") Long userId, @Param("currencyId") Long currencyId );

    @Query("SELECT NEW com.aex.platform.entities.dtos.PaymentResumeDto(p.currency.id, p.currencyCode, p.currency.description, SUM(p.amount)) "
            + "FROM Payment p "
            + "WHERE p.user.id = :userId "
            + "AND p.status = 'aprobado' "
            + "GROUP BY p.currency.id, p.currency.description, p.currencyCode")
    List<PaymentResumeDto> getResumePayments(@Param("userId") Long userId);

    List<Payment> findAllByUserIdAndCurrencyId(Long userId, Long currencyId);
}
