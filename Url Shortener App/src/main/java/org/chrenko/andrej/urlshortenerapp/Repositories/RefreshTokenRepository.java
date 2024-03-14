package org.chrenko.andrej.urlshortenerapp.Repositories;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.RefreshToken;
import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findRefreshTokenById(UUID uuid);

  Optional<RefreshToken> findRefreshTokenByUser(User user);
}
