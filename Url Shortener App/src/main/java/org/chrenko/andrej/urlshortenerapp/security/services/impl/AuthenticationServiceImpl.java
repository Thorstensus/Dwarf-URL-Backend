package org.chrenko.andrej.urlshortenerapp.security.services.impl;

import org.chrenko.andrej.urlshortenerapp.entities.RefreshToken;
import org.chrenko.andrej.urlshortenerapp.entities.User;
import org.chrenko.andrej.urlshortenerapp.dto.authentication.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.dto.authentication.AuthenticationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.enums.Role;
import org.chrenko.andrej.urlshortenerapp.exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.repositories.UserRepository;
import org.chrenko.andrej.urlshortenerapp.security.services.AuthenticationService;
import org.chrenko.andrej.urlshortenerapp.security.services.JwtService;
import org.chrenko.andrej.urlshortenerapp.security.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final ExceptionService exceptionService;

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  private final JwtService jwtService;

  private final RefreshTokenService refreshTokenService;

  @Autowired
  public AuthenticationServiceImpl(ExceptionService exceptionService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService) {
    this.exceptionService = exceptionService;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.refreshTokenService = refreshTokenService;
  }

  @Override
  public AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO requestDTO) {
    exceptionService.checkForAuthenticationErrors(requestDTO);

    User authenticatedUser = userRepository.findByEmail(requestDTO.getEmail()).get();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));

    HashMap<String, Object> extraClaims = new HashMap<>();

    extraClaims.put("userId", authenticatedUser.getId());
    extraClaims.put("isAdmin", authenticatedUser.getRole() == Role.ADMIN);
    extraClaims.put("isVerified", authenticatedUser.isVerified());

    String jwtToken = jwtService.generateToken(extraClaims, authenticatedUser);

    RefreshToken refreshToken;

    if (authenticatedUser.getRefreshToken() == null) {
      refreshToken = refreshTokenService.createRefreshToken(authenticatedUser.getUsername());
      refreshTokenService.saveRefreshToken(refreshToken);
    } else {
      refreshToken = authenticatedUser.getRefreshToken();
      if (refreshToken.getExpiryDate().before(new Date())) {
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + refreshTokenService.getExpirationTime()));
        refreshTokenService.saveRefreshToken(refreshToken);
      }
    }

    return new AuthenticationResponseDTO("ok", refreshToken.getId(), jwtToken);
  }
}
