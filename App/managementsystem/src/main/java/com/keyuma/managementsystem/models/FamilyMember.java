package com.keyuma.managementsystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "family_members")
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EGender gender;

    @NotNull
    private LocalDate birthDate;

    @Size(max = 15)
    private String phone;

    @Size(max = 50)
    private String email;

    @Size(max = 100)
    private String address1;

    @Size(max = 100)
    private String address2;

    @Size(max = 100)
    private String address3;

    @Size(max = 10)
    private String postcode;

    @Size(max = 50)
    private String city;

    @Size(max = 50)
    private String country;

    private Boolean statusMarried;

    @Size(max = 100)
    private String work;

    @OneToMany(mappedBy = "parent")
    private Set<FamilyMember> children = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FamilyMember parent;

    @ManyToOne
    @JoinColumn(name = "spouse_id")
    private FamilyMember spouse;

}