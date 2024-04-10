package org.chrenko.andrej.urlshortenerapp.Repositories;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByName(String name);

  @Query("SELECT u FROM User u LEFT JOIN u.urls url LEFT JOIN url.clicks c GROUP BY u ORDER BY COUNT(c) DESC")
  Page<User> findAllByTotalClicksDesc(Pageable pageable);
}
