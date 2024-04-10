package org.chrenko.andrej.urlshortenerapp.Repositories;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.ShortenedURL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShortenedURLRepository extends JpaRepository<ShortenedURL, UUID> {
  Optional<ShortenedURL> findShortenedURLByLink(String link);

  @Query("SELECT l FROM ShortenedURL l LEFT JOIN l.clicks clicks GROUP BY l ORDER BY COUNT(clicks) DESC")
  Page<ShortenedURL> findShortenedURLSbyClickCount(Pageable pageable);
}
