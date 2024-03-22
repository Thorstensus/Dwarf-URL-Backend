package org.chrenko.andrej.urlshortenerapp.Security.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.RefreshToken;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationRequestDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.AuthenticationResponseDTO;
import org.chrenko.andrej.urlshortenerapp.Enum.Role;
import org.chrenko.andrej.urlshortenerapp.Exceptions.ExceptionService;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.chrenko.andrej.urlshortenerapp.Security.Services.AuthenticationService;
import org.chrenko.andrej.urlshortenerapp.Security.Services.JwtService;
import org.chrenko.andrej.urlshortenerapp.Security.Services.RefreshTokenService;
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
      refreshToken = refreshTokenService.createRefreshToken(authenticatedUser.getEmail());
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
