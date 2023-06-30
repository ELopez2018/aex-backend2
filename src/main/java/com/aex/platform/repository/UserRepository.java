/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.aex.platform.repository;

import com.aex.platform.entities.Role;
import com.aex.platform.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author estar
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);

    List<User> findAll();

    Optional<User> findOneByEmail(String email);

    Optional<List<User>> findAllByRole(Role role);
    Optional<User> findByDocumentTypeAndDocumentNumber(String documentType, Long documentNumber );

}
