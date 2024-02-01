package com.aex.platform.repository;

import com.aex.platform.entities.MobilePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MobilePaymentRepository extends JpaRepository<MobilePayment, Long> {

    List<MobilePayment> findAllByStatusIn(Collection<Long> statusId);

    List<MobilePayment> findAllByClientIdIn(Collection<Long> clientId);
}
