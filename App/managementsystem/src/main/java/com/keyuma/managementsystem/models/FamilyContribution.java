package com.keyuma.managementsystem.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "family_contributions")
public class FamilyContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "familyContribution", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contribution> contributions = new HashSet<>();

    // Getters and setters
}
