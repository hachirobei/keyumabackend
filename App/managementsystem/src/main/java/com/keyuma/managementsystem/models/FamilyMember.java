package com.keyuma.managementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
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
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @NotNull
    private Date birthDate;

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
    
    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaction> transactions = new HashSet<>();

    public FamilyMember(String firstName, String lastName, Gender gender, Date birthDate, String phone, String email, String address1, String address2, String address3, String postcode, String city, String country, Boolean statusMarried, String work) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
        this.statusMarried = statusMarried;
        this.work = work;
    }
}
