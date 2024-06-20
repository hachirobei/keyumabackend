package com.keyuma.managementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contribution_id", nullable = false)
    private Contribution contribution;

    @ManyToOne
    @JoinColumn(name = "family_member_id", nullable = false)
    private FamilyMember familyMember;

    @NotNull
    @Column(nullable = false)
    private Double amount;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private EVerificationStatus isVerify = EVerificationStatus.REJECTED;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
}
