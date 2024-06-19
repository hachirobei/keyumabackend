package com.keyuma.managementsystem.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContributionPlanRequest {

    @NotNull
    private Double defaultMonthlyAmount;

    @NotNull
    private Double defaultYearlyAmount;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;
}
