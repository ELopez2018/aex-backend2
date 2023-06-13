package com.afrac.serviceorders.repository;

import com.afrac.serviceorders.entities.VehicleReception;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleReceptionRepository extends JpaRepository<VehicleReception, Long> {
}
