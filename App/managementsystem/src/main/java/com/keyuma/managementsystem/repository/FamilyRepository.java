package com.keyuma.managementsystem.repository;

import com.keyuma.managementsystem.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long> {
}
