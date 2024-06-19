package com.keyuma.managementsystem.payload.request;

import com.keyuma.managementsystem.models.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class FamilyMemberRequest {

    @NotNull
    @Max(100)
    private String firstName;

    @Null
    @Max(100)
    private String lastName;

    @NotNull
    @Size(max = 15)
    private String phone;

    @NotNull
    private Gender gender;

    @NotNull
    private Date birthdate;

    @Null
    @Email
    @Size(max = 50)
    private String email;

    @NotNull
    @Size(max = 100)
    private String address1;

    @NotNull
    @Size(max = 100)
    private String address2;

    @NotNull
    @Size(max = 100)
    private String address3;

    @NotNull
    @Size(max = 50)
    private String city;

    @NotNull
    @Pattern(regexp = "[0-9]{5}" )
    private String postcode;

    @NotNull
    @Size(max = 50)
    private String country;

    @NotNull
    private Boolean statusMarried;

    @Null
    @Size(max = 100)
    private String work;

    @Null
    private Integer user_id;

}
