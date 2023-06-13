package com.afrac.serviceorders.service;

import com.afrac.serviceorders.auth.AuthenticationResponse;
import com.afrac.serviceorders.auth.RegisterRequest;
import com.afrac.serviceorders.config.JwtService;
import com.afrac.serviceorders.entities.User;
import com.afrac.serviceorders.repository.UserRepository;
import com.afrac.serviceorders.token.Token;
import com.afrac.serviceorders.token.TokenRepository;
import com.afrac.serviceorders.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  public AuthenticationResponse register(User request) {
    User user;
    user = User.builder()
    .nickname(request.getNickname())
    .password(passwordEncoder.encode(request.getPassword()))
    .names(request.getNames())
    .lastNames(request.getLastNames())
    .documentNumber(request.getDocumentNumber())
    .documentType(request.getDocumentType())
    .cellPhone(request.getCellPhone())
    .phone(request.getPhone())
    .email(request.getEmail())
    .role(request.getRole())
    .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }
}
