package com.keyuma.managementsystem.payload.response;

import java.util.Date;

public class ContributionPlanDTO {

    private Long id;
    private String name;
    private Integer year;
    private Double defaultMonthlyAmount;
    private Double defaultYearlyAmount;
    private Date startDate;
    private Date endDate;

    public ContributionPlanDTO(Long id, String name, Integer year, Double defaultMonthlyAmount, Double defaultYearlyAmount, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.defaultMonthlyAmount = defaultMonthlyAmount;
        this.defaultYearlyAmount = defaultYearlyAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
