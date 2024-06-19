package com.keyuma.managementsystem.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContributionPlanRequest {

    @NotBlank
    private String name;

    @NotBlank
    private Integer year;

    @NotBlank
    private Double defaultMonthlyAmount;

    @NotBlank
    private Double defaultYearlyAmount;

    @NotBlank
    private Date startDate;

    @NotBlank
    private Date endDate;
}
