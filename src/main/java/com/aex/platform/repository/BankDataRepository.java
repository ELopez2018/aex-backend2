/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aex.platform.repository;

import com.aex.platform.entities.BankData;
import com.aex.platform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author estar
 */
public interface BankDataRepository extends JpaRepository<BankData, Long> {
    Optional<BankData> findByAccountNumber(String documentNumber );
    Optional<List<BankData>> findAllByUserId(Long userId );
}
