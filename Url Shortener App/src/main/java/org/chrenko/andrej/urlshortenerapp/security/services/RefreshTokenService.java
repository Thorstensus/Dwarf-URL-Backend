package org.chrenko.andrej.urlshortenerapp.security.services;

import org.chrenko.andrej.urlshortenerapp.entities.RefreshToken;
import org.chrenko.andrej.urlshortenerapp.entities.User;
import org.chrenko.andrej.urlshortenerapp.dto.refreshaccesstoken.RefreshAccessResponseDTO;
import org.chrenko.andrej.urlshortenerapp.dto.refreshaccesstoken.RefreshAccessRequestDTO;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
  RefreshToken createRefreshToken(String email);

  Optional<RefreshToken> findByUUID(UUID uuid);

  Optional<RefreshToken> findByUser(User user);

  RefreshToken verifyExpiration(RefreshToken token);

  void saveRefreshToken(RefreshToken refreshToken);

  void deleteRefreshToken(RefreshToken refreshToken);

  RefreshAccessResponseDTO generateNewToken(RefreshAccessRequestDTO requestDTO);

  int getExpirationTime();
}
