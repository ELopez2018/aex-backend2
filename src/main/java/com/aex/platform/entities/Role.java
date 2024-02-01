/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aex.platform.entities;

/**
 *
 * @author estar
 */
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  Permission.ADMIN_READ,
                  Permission.ADMIN_UPDATE,
                  Permission.ADMIN_DELETE,
                  Permission.ADMIN_CREATE,
                  Permission.MANAGER_READ,
                  Permission.MANAGER_UPDATE,
                  Permission.MANAGER_DELETE,
                  Permission.MANAGER_CREATE
          )
  ),
  MANAGER(
      Set.of(
          Permission.MANAGER_READ,
          Permission.MANAGER_UPDATE,
          Permission.MANAGER_DELETE,
          Permission.MANAGER_CREATE
      )
  ),
  CASHIER(
          Set.of(
                  Permission.MANAGER_READ,
                  Permission.MANAGER_UPDATE,
                  Permission.MANAGER_DELETE,
                  Permission.MANAGER_CREATE
          )
  ),
  CORRESPONDENT(
      Set.of(
          Permission.MANAGER_READ,
          Permission.MANAGER_UPDATE,
          Permission.MANAGER_DELETE,
          Permission.MANAGER_CREATE
      )
  ),
  CLIENT(
      Set.of(
          Permission.MANAGER_READ
      )
  ),
  GUEST(
          Set.of(
                  Permission.MANAGER_READ
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
