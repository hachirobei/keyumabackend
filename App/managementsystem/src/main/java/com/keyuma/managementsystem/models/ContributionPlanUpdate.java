package com.keyuma.managementsystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contribution_plan_updates")
public class ContributionPlanUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contribution_plan_id", nullable = false)
    private ContributionPlan contributionPlan;

    @Column(nullable = false)
    private Double monthlyAmount;

    @Column(nullable = false)
    private Double yearlyAmount;

    @Column(nullable = false)
    private LocalDate effectiveDate;

    // Getters and setters
}
