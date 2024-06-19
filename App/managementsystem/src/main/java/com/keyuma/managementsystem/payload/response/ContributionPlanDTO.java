package com.keyuma.managementsystem.payload.response;

import java.util.Date;

public class ContributionPlanDTO {

    private Long id;
    private Double defaultMonthlyAmount;
    private Double defaultYearlyAmount;
    private Date startDate;
    private Date endDate;

    public ContributionPlanDTO(Long id, Double defaultMonthlyAmount, Double defaultYearlyAmount, Date startDate, Date endDate) {
        this.id = id;
        this.defaultMonthlyAmount = defaultMonthlyAmount;
        this.defaultYearlyAmount = defaultYearlyAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
