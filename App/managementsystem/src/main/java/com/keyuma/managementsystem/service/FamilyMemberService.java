package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.FamilyException;
import com.keyuma.managementsystem.exception.FamilyMemberException;
import com.keyuma.managementsystem.models.Family;
import com.keyuma.managementsystem.models.FamilyMember;
import com.keyuma.managementsystem.payload.request.FamilyMemberRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    public PagedApiResponse<FamilyMember, FamilyMemberDTO> getAllFamilyMember(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FamilyMember> familyMemberPage = familyMemberRepository.findAll(pageable);

        List<FamilyMemberDTO> familyMemberDTOs = familyMemberPage.getContent().stream()
                .map(familyMember -> new FamilyMemberDTO(
                                familyMember.getId(),
                                familyMember.getFirstName(),
                                familyMember.getLastName(),
                                familyMember.getPhone(),
                                familyMember.getGender().getName().toString(),
                                familyMember.getBirthDate(),
                                familyMember.getEmail(),
                                familyMember.getAddress1(),
                                familyMember.getAddress2(),
                                familyMember.getAddress3(),
                                familyMember.getPostcode(),
                                familyMember.getCity(),
                                familyMember.getCountry(),
                                familyMember.getStatusMarried(),
                                familyMember.getWork()
                        )
                ).collect(Collectors.toList());

        return new PagedApiResponse<>(true, "All family retrieved successfully", familyMemberPage, familyMemberDTOs);
    }

    public FamilyMemberDTO getFamilyMemberById(Long id) {
        FamilyMember familyMember = familyMemberRepository.findById(id)
                .orElseThrow(() -> new FamilyMemberException("Family member not found!"));

        return new FamilyMemberDTO(
                familyMember.getId(),
                familyMember.getFirstName(),
                familyMember.getLastName(),
                familyMember.getPhone(),
                familyMember.getGender().getName().toString(),
                familyMember.getBirthDate(),
                familyMember.getEmail(),
                familyMember.getAddress1(),
                familyMember.getAddress2(),
                familyMember.getAddress3(),
                familyMember.getPostcode(),
                familyMember.getCity(),
                familyMember.getCountry(),
                familyMember.getStatusMarried(),
                familyMember.getWork()
        );
    }


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
