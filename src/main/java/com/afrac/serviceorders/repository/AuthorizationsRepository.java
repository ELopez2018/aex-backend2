package com.afrac.serviceorders.repository;

import com.afrac.serviceorders.entities.Authorizations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationsRepository extends JpaRepository<Authorizations, Long> {
}
