package com.keyuma.managementsystem.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ContributionRequest {

    @NotNull
    private Long ContributionPlanId;

    @NotNull
    private Long familyMemberId;

    @NotNull
    private Date date;
}
