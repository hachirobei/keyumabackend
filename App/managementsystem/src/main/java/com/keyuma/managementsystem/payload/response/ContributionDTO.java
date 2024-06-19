package com.keyuma.managementsystem.payload.response;

import java.util.Date;

public class ContributionDTO {

    private Long id;
    private Long contributionPlanId;
    private Long familyMemberId;
    private Date date;

    public ContributionDTO(Long id, Long contributionPlanId, Long familyMemberId, Date date) {
        this.id = id;
        this.contributionPlanId = contributionPlanId;
        this.familyMemberId = familyMemberId;
        this.date = date;
    }
}
