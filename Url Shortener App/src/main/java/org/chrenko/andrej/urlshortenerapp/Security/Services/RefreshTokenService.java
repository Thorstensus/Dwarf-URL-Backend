package org.chrenko.andrej.urlshortenerapp.Security.Services;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.RefreshToken;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessResponseDTO;
import org.chrenko.andrej.urlshortenerapp.DTOs.Refresh_Access_Token.RefreshAccessRequestDTO;

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
