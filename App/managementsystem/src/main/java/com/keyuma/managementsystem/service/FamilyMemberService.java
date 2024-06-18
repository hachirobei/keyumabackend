package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.UserException;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    public ApiResponse<MessageResponse> deleteFamilyMember(Long id) {
        familyMemberRepository.findById(id)
                .orElseThrow(() -> new UserException("Family member not found!"));

        familyMemberRepository.deleteById(id);
        return new ApiResponse<>(true, "Family member deleted successfully", new MessageResponse("Family member deleted successfully!"));
    }
}
