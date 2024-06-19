package com.keyuma.managementsystem.repository;

import com.keyuma.managementsystem.models.ContributionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionPlanRepository extends JpaRepository<ContributionPlan,Long> {

    boolean existByName(String name);

    boolean existByYear(Integer year);
}
