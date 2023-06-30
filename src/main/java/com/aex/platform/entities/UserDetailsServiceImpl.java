package com.aex.security;

import com.aex.entities.User;
import com.aex.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findOneByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("El usuario con el email " + email + " no existe."));
    return new UserDetailsImpl(user);
  }
}
