package com.keyuma.managementsystem.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionRequest {

    @NotNull
    private Long contributionId;

    @NotNull
    private Long familyMemberId;

    @NotNull
    private Double amount;

    @NotNull
    private Integer isVerify;

    @NotNull
    private Date transactionDate;
}
