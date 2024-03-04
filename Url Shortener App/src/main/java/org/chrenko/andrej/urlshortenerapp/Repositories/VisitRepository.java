package org.chrenko.andrej.urlshortenerapp.Repositories;

import org.chrenko.andrej.urlshortenerapp.DB_Entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
