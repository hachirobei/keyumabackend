package com.keyuma.managementsystem.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TransactionDTO {
    private Long id;
    private Long contributionId;
    private Long familyMemberId;
    private Double amount;
    private Integer isVerify;
    private Date transactionDate;

    public TransactionDTO(Long id, Long contributionId,Long familyMemberId, Double amount, Integer isVerify, Date transactionDate) {
        this.id = id;
        this.contributionId = contributionId;
        this.familyMemberId = familyMemberId;
        this.amount = amount;
        this.isVerify = isVerify;
        this.transactionDate = transactionDate;
    }
}
