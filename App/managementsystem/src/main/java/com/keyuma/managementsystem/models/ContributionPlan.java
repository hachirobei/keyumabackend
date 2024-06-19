package com.keyuma.managementsystem.models;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private String name;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Double defaultMonthlyAmount;

    @Column(nullable = false)
    private Double defaultYearlyAmount;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    public ContributionPlan(String name, Integer year, Double defaultMonthlyAmount, Double defaultYearlyAmount, Date startDate, Date endDate) {
        this.name = name;
        this.year = year;
        this.defaultMonthlyAmount = defaultMonthlyAmount;
        this.defaultYearlyAmount = defaultYearlyAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
