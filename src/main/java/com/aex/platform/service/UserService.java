package com.aex.platform.service;

import com.aex.platform.auth.AuthenticationResponse;
import com.aex.platform.config.JwtService;
import com.aex.platform.entities.User;
import com.aex.platform.repository.UserRepository;
import com.aex.platform.token.Token;
import com.aex.platform.token.TokenRepository;
import com.aex.platform.token.TokenType;
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
        .firstName(request.getFirstName())
        .secondName(request.getSecondName())
        .surname(request.getSurname())
        .maximumAmount(request.getMaximumAmount())
        .balance(request.getBalance())

        .fullName(request.getFullName())
        .postpaid(request.getPostpaid())
        .lastName(request.getLastName())
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
