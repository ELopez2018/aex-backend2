package com.aex.platform.repository;

import com.aex.platform.entities.MobilePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MobilePaymentRepository extends JpaRepository<MobilePayment, Long> {

    List<MobilePayment> findAllByStatusIn(Collection<Long> statusId);

    List<MobilePayment> findAllByClientIdIn(Collection<Long> clientId);

    @Query("SELECT SUM(mp.valueToSend) FROM MobilePayment mp "
            + "WHERE 1=1 "
            + "AND mp.correspondent.user.id = :userId "
            + "AND mp.status <> 3"
            + "AND mp.currencyFrom = :currencyCode "
    )
    Double getMobilePaymentnTotal(@Param("userId") Long userId, @Param("currencyCode") String currencyCode);

    @Query("SELECT SUM(mp.valueReceive) FROM MobilePayment mp "
            + "WHERE 1=1 "
            + "AND mp.cashier.id= :userId "
            + "AND mp.currencyTo= :currencyCode "
            + "AND mp.status = 2"
    )
    Double getMobilePaymentnByCashier(@Param("userId") Long userId, @Param("currencyCode") String currencyCode );
}
