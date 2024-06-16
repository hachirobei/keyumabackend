package com.keyuma.managementsystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "family_relationships")
public class FamilyRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_member_id", nullable = false)
    private FamilyMember familyMember;

    @ManyToOne
    @JoinColumn(name = "related_family_member_id", nullable = false)
    private FamilyMember relatedFamilyMember;

    @NotBlank
    @Size(max = 20)
    private String relationshipType; // e.g., "parent", "child", "spouse"

}
