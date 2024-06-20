package com.keyuma.managementsystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "families")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FamilyMember> members = new HashSet<>();

    public Family(String name) {
        this.name = name;
        this.members = new HashSet<>();
    }
}
