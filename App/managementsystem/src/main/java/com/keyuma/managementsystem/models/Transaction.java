package com.keyuma.managementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contribution_id", nullable = false)
    private Contribution contribution;

    @NotNull
    private Double amount;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private VerificationStatus isVerify = VerificationStatus.REJECTED;

    @NotNull
    private Date transactionDate;
}
