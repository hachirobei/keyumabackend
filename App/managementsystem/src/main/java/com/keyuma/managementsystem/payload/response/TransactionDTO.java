package com.keyuma.managementsystem.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TransactionDTO {
    private Long id;
    private Long contributionId;
    private Double amount;
    private Integer isVerify;
    private Date transactionDate;

    public TransactionDTO(Long id, Long contributionId, Double amount, Integer isVerify, Date transactionDate) {
        this.id = id;
        this.contributionId = contributionId;
        this.amount = amount;
        this.isVerify = isVerify;
        this.transactionDate = transactionDate;
    }
}
