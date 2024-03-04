package org.chrenko.andrej.urlshortenerapp.Repositories;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
