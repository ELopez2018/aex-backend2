package com.aex.platform.config;

import com.aex.platform.token.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
//  protected void doFilterInternal(
//      @NonNull HttpServletRequest request,
//      @NonNull HttpServletResponse response,
//      @NonNull FilterChain filterChain
//  ) throws ServletException, IOException {
//    if (request.getServletPath().contains("/api/v1/auth")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//    final String authHeader = request.getHeader("Authorization");
//    final String jwt;
//    final String userEmail;
//    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//    jwt = authHeader.substring(7);
//    userEmail = jwtService.extractUsername(jwt);
//    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//      var isTokenValid = tokenRepository.findByToken(jwt)
//          .map(t -> !t.isExpired() && !t.isRevoked())
//          .orElse(false);
//      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//            userDetails,
//            null,
//            userDetails.getAuthorities()
//        );
//        authToken.setDetails(
//            new WebAuthenticationDetailsSource().buildDetails(request)
//        );
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//      }
//    }
//    filterChain.doFilter(request, response);
//  }


// ...

//  protected void doFilterInternal(
//          @NonNull HttpServletRequest request,
//          @NonNull HttpServletResponse response,
//          @NonNull FilterChain filterChain
//  ) throws ServletException, IOException {
//    if (request.getServletPath().contains("/api/v1/auth")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//    final String authHeader = request.getHeader("Authorization");
//    final String jwt;
//    final String userEmail;
//    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//    jwt = authHeader.substring(7);
//    try {
//      userEmail = jwtService.extractUsername(jwt);
//      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//        var isTokenValid = tokenRepository.findByToken(jwt)
//                .map(t -> !t.isExpired() && !t.isRevoked())
//                .orElse(false);
//        if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
//          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                  userDetails,
//                  null,
//                  userDetails.getAuthorities()
//          );
//          authToken.setDetails(
//                  new WebAuthenticationDetailsSource().buildDetails(request)
//          );
//          SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//      }
//    } catch (ExpiredJwtException e) {
//      // Manejar JWT expirado
//      response.setStatus(HttpStatus.UNAUTHORIZED.value());
//      response.getWriter().write("El token ha expiado.");
//      return;
//    }
//    filterChain.doFilter(request, response);
//  }
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        try {
            log.info("Verificando token");
            userEmail = jwtService.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                var isTokenValid = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);
                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("El token es invalido.");
                    log.info("El token en db : " + isTokenValid + " - Comprobacin de Usuario: " + jwtService.isTokenValid(jwt, userDetails));
                    return;
                }
            }
        } catch (ExpiredJwtException e) {
            // Manejar JWT expirado
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("El token ha expirado.");
            log.info("El token esta expirado. Details: " + e.getMessage());
            return;
        } catch (Exception e) {
            // Manejar otros errores de JWT
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("El token es invalido.");
            log.info("El token es inválido. Details: " + e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

}
