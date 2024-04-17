package org.chrenko.andrej.urlshortenerapp.repositories;

import org.chrenko.andrej.urlshortenerapp.entities.RefreshToken;
import org.chrenko.andrej.urlshortenerapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findRefreshTokenById(UUID uuid);

  Optional<RefreshToken> findRefreshTokenByUser(User user);
}
