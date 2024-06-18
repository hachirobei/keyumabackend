package com.keyuma.managementsystem.repository;

import com.keyuma.managementsystem.models.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenderRepository extends JpaRepository<Gender, Long> {
    Optional<Gender> findByName(Gender name);
}
