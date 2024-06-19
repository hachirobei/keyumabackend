package com.keyuma.managementsystem.payload.response;

import java.util.Date;

public class ContributionDTO {

    private Long id;
    private Long contributionPlanId;
    private Long familyMemberId;
    private Double amount;
    private Date date;

    public ContributionDTO(Long id, Long contributionPlanId, Long familyMemberId, Double amount, Date date) {
        this.id = id;
        this.contributionPlanId = contributionPlanId;
        this.familyMemberId = familyMemberId;
        this.amount = amount;
        this.date = date;
    }
}
