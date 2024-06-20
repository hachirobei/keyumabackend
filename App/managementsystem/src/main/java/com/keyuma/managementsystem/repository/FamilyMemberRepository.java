package com.keyuma.managementsystem.repository;

import com.keyuma.managementsystem.models.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember,Long> {
}
