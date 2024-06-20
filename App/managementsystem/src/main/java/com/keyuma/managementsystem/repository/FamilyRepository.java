package com.keyuma.managementsystem.repository;

import com.keyuma.managementsystem.models.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Query("SELECT f FROM Family f")
    Page<Family> findAllById(Pageable pageable);
}
