package com.keyuma.managementsystem.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FamilyMemberDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private Date birthdate;
    private String email;
    private String address1;
    private String address2;
    private String address3;
    private Integer postcode;
    private String city;
    private String country;
    private Boolean statusMarried;
    private String work;
    private String user_id;


    public FamilyMemberDTO(Long id, String firstName, String lastName, String phone, String gender, Date birthdate, String email, String address1, String address2, String address3, Integer postcode, String city, String country, Boolean statusMarried, String work, String user_id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
        this.statusMarried = statusMarried;
        this.work = work;
        this.user_id = user_id;
    }
}
