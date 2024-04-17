package org.chrenko.andrej.urlshortenerapp.repositories;

import org.chrenko.andrej.urlshortenerapp.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, String> {
}
