package com.keyuma.managementsystem.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FamilyDTO {

    private Long Id;
    private String name;

    public FamilyDTO(Long Id, String name){
        this.Id = Id;
        this.name = name;
    }
}
