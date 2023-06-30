/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.afrac.serviceorders.repository;

import com.afrac.serviceorders.entities.Role;
import com.afrac.serviceorders.entities.User;

import java.util.List;
import java.util.Optional;

import com.afrac.serviceorders.entities.dtos.UserDTO;
import jakarta.persistence.EnumType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author estar
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);

    Optional<List<User>> findAllByRole(Role role);
    Optional<User> findByDocumentTypeAndDocumentNumber(String documentType, Long documentNumber );

}
