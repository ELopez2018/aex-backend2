package com.aex.platform.repository;

import com.aex.platform.entities.Municipality;
import com.aex.platform.entities.Parish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParishRepository  extends JpaRepository<Parish, Long> {

  List<Parish> findAllByMunicipalityId(Long municipalityId);
}
