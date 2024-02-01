/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.aex.platform.repository;

import com.aex.platform.entities.Role;
import com.aex.platform.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author estar
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    Optional<User> findOneByEmail(String email);

    Optional<List<User>> findAllByRole(Role role);

    Optional<User> findByDocumentTypeAndDocumentNumber(String documentType, Long documentNumber);

    Optional<User> findByDocumentNumber(Long documentNumber);

    //Optional<List<User>> findAllByDocumentNumberOrFullName( String query, String query1 );
    @Query("SELECT DISTINCT u FROM User u "
            + "WHERE 1=1 "
            + "AND (:documentNumber IS NULL OR u.documentNumber = :documentNumber) "
            + "OR u.fullName  LIKE  UPPER(concat('%', :query, '%'))"
    )
    Page<User> findAllUserCustom(@Param("documentNumber") Long documentNumber, @Param("query") String query, Pageable pageable);
}
