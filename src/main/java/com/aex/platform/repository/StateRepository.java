package com.aex.platform.repository;

import com.aex.platform.entities.Payments;
import com.aex.platform.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {
  List<State> findAllByCountryId(Long countryId);
}
