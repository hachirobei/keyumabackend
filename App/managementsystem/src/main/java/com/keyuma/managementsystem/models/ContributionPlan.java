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
@Table(name = "contribution_plans")
public class ContributionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double defaultMonthlyAmount;

    @Column(nullable = false)
    private Double defaultYearlyAmount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;

    @OneToMany(mappedBy = "contributionPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ContributionPlanUpdate> updates = new HashSet<>();

    // Getters and setters
}
