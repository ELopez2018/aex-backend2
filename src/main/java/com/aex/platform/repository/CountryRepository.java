package com.aex.platform.repository;

import com.aex.platform.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository  extends JpaRepository<Country, Long> {
}
