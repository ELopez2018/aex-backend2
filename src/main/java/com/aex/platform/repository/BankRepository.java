/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.repository;

import com.aex.platform.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author estar
 */
public interface BankRepository extends JpaRepository<Bank, Long> {
  List<Bank> findAllByCountryId(Long countryId);

  Optional<Bank> findByCode(String code);

  Optional<Bank> findByName(String bankName);
}
