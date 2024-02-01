/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.aex.platform.repository;

import com.aex.platform.entities.Correspondent;
import com.aex.platform.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author estar
 */
public interface CorrespondentRepository extends JpaRepository<Correspondent, Long> {

    Optional<Correspondent> findByUserId(Long userId);

    Optional<List<Correspondent>> findAllByUserId(Long userId);
    
}
