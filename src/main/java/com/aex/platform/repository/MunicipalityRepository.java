package com.aex.platform.repository;

import com.aex.platform.entities.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
  List<Municipality> findAllByStateId(Long id);
}
