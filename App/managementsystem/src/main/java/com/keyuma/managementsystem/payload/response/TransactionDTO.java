package com.keyuma.managementsystem.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TransactionDTO {
    private Long id;
    private Long contributionId;
    private double amount;
    private Date transactionDate;

    public TransactionDTO(Long id, Long contributionId, double amount, Date transactionDate) {
        this.id = id;
        this.contributionId = contributionId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}
