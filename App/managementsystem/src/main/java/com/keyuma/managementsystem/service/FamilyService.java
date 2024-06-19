package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.FamilyException;
import com.keyuma.managementsystem.exception.UserException;
import com.keyuma.managementsystem.models.Family;
import com.keyuma.managementsystem.models.User;
import com.keyuma.managementsystem.payload.request.FamilyRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;

    public PagedApiResponse<Family, FamilyDTO> getAllFamily(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Family> familyPage = familyRepository.findAll(pageable);

        List<FamilyDTO> familyDTOs = familyPage.getContent().stream()
                .map(family -> new FamilyDTO(
                        family.getId(),
                        family.getName()))
                .collect(Collectors.toList());

        return new PagedApiResponse<>(true, "All family retrieved successfully", familyPage, familyDTOs);
    }

    public FamilyDTO getFamilyById(Long id) {
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new FamilyException("Family not found!"));

        return new FamilyDTO(
                family.getId(),
                family.getName()
        );
    }

    public ApiResponse<MessageResponse> addFamily(FamilyRequest familyRequest){

        Family family = new Family(familyRequest.getName());
        familyRepository.save(family);

        return new ApiResponse<>(true, "Family adding successfully", new MessageResponse("Family adding successfully!"));
    }

    public ApiResponse<MessageResponse> updateFamily(Long id, FamilyRequest familyRequest){
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new FamilyException("Family not found"));

        family.setName(familyRequest.getName());
        familyRepository.save(family);
        return new ApiResponse<>(true, "Family updating successfully", new MessageResponse("Family updating successfully!"));
    }

    public ApiResponse<MessageResponse> deleteFamily(Long id) {
        familyRepository.findById(id)
                .orElseThrow(() -> new FamilyException("Family not found!"));

        familyRepository.deleteById(id);
        return new ApiResponse<>(true, "Family deleted successfully", new MessageResponse("Family deleted successfully!"));
    }
}
