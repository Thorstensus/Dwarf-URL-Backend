package org.chrenko.andrej.urlshortenerapp.Security.Services.Impl;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.RefreshToken;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessResponseDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessRequestDTO;
import org.chrenko.andrej.urlshortenerapp.Exceptions.DTOs.ApiRequestException;
import org.chrenko.andrej.urlshortenerapp.Repositories.RefreshTokenRepository;
import org.chrenko.andrej.urlshortenerapp.Repositories.UserRepository;
import org.chrenko.andrej.urlshortenerapp.Security.Services.JwtService;
import org.chrenko.andrej.urlshortenerapp.Security.Services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  private final UserRepository userRepository;

  @Value("${REFRESH_EXPIRATION_TIME}")
  private String REFRESH_EXPIRATION_TIME;

  private final JwtService jwtService;

  @Autowired
  public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtService jwtService) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
  }

  @Override
  public RefreshToken createRefreshToken(String email) {
    Optional<User> userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      RefreshToken refreshToken = new RefreshToken(new Date(System.currentTimeMillis() + Integer.parseInt(REFRESH_EXPIRATION_TIME)),
          user);
      refreshTokenRepository.save(refreshToken);
      user.setRefreshToken(refreshToken);
      userRepository.save(user);
      return refreshToken;
    } else {
      throw new ApiRequestException("/api","Unknown Error");
    }
  }

  @Override
  public Optional<RefreshToken> findByUUID(UUID uuid) {
    return refreshTokenRepository.findRefreshTokenById(uuid);
  }

  @Override
  public Optional<RefreshToken> findByUser(User user) {
    return refreshTokenRepository.findRefreshTokenByUser(user);
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().before(new Date())) {
      token.getUser().setRefreshToken(null);
      userRepository.save(token.getUser());
      deleteRefreshToken(token);
      throw new ApiRequestException("/api/refresh-token",token.getId().toString() + ": Refresh token is expired. Please log in again!");
    }
    return token;
  }

  @Override
  public void saveRefreshToken(RefreshToken refreshToken) {
    refreshTokenRepository.save(refreshToken);
  }

  @Override
  public void deleteRefreshToken(RefreshToken refreshToken) {
    refreshTokenRepository.delete(refreshToken);
  }

  @Override
  public RefreshAccessResponseDTO generateNewToken(RefreshAccessRequestDTO requestDTO) {
    return findByUUID(requestDTO.getUuid())
        .map(this::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String accessToken = jwtService.generateToken(user);
          return new RefreshAccessResponseDTO(requestDTO.getUuid(), accessToken);
        }).orElseThrow(() -> new ApiRequestException("/api/refresh-token", "Refresh Token does not exist!"));
  }

  @Override
  public int getExpirationTime() {
    return Integer.parseInt(REFRESH_EXPIRATION_TIME);
  }
}
