package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.FamilyMemberException;
import com.keyuma.managementsystem.models.FamilyMember;
import com.keyuma.managementsystem.payload.request.FamilyMemberRequest;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    public ApiResponse<MessageResponse> addFamilyMember(FamilyMemberRequest familyMemberRequest){

        FamilyMember familyMember = new FamilyMember(familyMemberRequest.getFirstName(),
                familyMemberRequest.getLastName(),
                familyMemberRequest.getGender(),
                familyMemberRequest.getBirthdate(),
                familyMemberRequest.getPhone(),
                familyMemberRequest.getEmail(),
                familyMemberRequest.getAddress1(),
                familyMemberRequest.getAddress2(),
                familyMemberRequest.getAddress3(),
                familyMemberRequest.getPostcode(),
                familyMemberRequest.getCity(),
                familyMemberRequest.getCountry(),
                familyMemberRequest.getStatusMarried(),
                familyMemberRequest.getWork()
                );

        familyMemberRepository.save(familyMember);

        return new ApiResponse<>(true, "Family member added successfully", new MessageResponse("Family member added successfully!"));
    }

    public ApiResponse<MessageResponse> updateFamilyMember(Long id, FamilyMemberRequest familyMemberRequest){
        FamilyMember familyMember = familyMemberRepository.findById(id)
                .orElseThrow(()-> new FamilyMemberException("Family member not found"));
        familyMember.setFirstName(familyMemberRequest.getFirstName());
        familyMemberRepository.save(familyMember);

        return new ApiResponse<>(true, "Family member updated successfully", new MessageResponse("Family member updated successfully!"));
    }

    public ApiResponse<MessageResponse> deleteFamilyMember(Long id) {
        familyMemberRepository.findById(id)
                .orElseThrow(() -> new FamilyMemberException("Family member not found!"));

        familyMemberRepository.deleteById(id);
        return new ApiResponse<>(true, "Family member deleted successfully", new MessageResponse("Family member deleted successfully!"));
    }
}
