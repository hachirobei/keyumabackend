package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.ContributionException;
import com.keyuma.managementsystem.models.Contribution;
import com.keyuma.managementsystem.models.ContributionPlan;
import com.keyuma.managementsystem.models.FamilyMember;
import com.keyuma.managementsystem.payload.request.ContributionRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.repository.ContributionPlanRepository;
import com.keyuma.managementsystem.repository.ContributionRepository;
import com.keyuma.managementsystem.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributionService {

    @Autowired
    ContributionRepository contributionRepository;

    @Autowired
    ContributionPlanRepository contributionPlanRepository;

    @Autowired
    FamilyMemberRepository familyMemberRepository;

    public PagedApiResponse<Contribution, ContributionDTO> getAllContribution(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Contribution> ContributionPage = contributionRepository.findAll(pageable);

        List<ContributionDTO> ContributionDTOs = ContributionPage.getContent().stream()
                .map(contribution -> new ContributionDTO(
                        contribution.getId(),
                        contribution.getContributionPlan().getId(),
                        contribution.getFamilyMember().getId(),
                        contribution.getDate()))
                .collect(Collectors.toList());

        return new PagedApiResponse<>(true, "All transaction retrieved successfully", ContributionPage, ContributionDTOs);
    }

    public ApiResponse<ContributionDTO> getContributionById(Long id){
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ContributionException("Contribution not found"));

        ContributionDTO contributionDTO = new ContributionDTO(
                contribution.getId(),
                contribution.getContributionPlan().getId(),
                contribution.getFamilyMember().getId(),
                contribution.getDate()
        );

        return new ApiResponse<>(true, "Transaction retrieved successfully",contributionDTO);
    }

    public ApiResponse<MessageResponse> addContribution(ContributionRequest contributionRequest){
        FamilyMember familyMember = familyMemberRepository.findById(contributionRequest.getFamilyMemberId())
                .orElseThrow(() -> new ContributionException("Family member not found"));

        ContributionPlan contributionPlan = contributionPlanRepository.findById(contributionRequest.getContributionPlanId())
                .orElseThrow(() -> new ContributionException("Contribution plan not found"));

        Contribution contribution = new Contribution();
        contribution.setContributionPlan(contributionPlan);
        contribution.setFamilyMember(familyMember);
        contribution.setDate(contributionRequest.getDate());

        contributionRepository.save(contribution);

        return new ApiResponse<>(true, "Contribution added successfully", new MessageResponse("Contribution added successfully!"));
    }

    public ApiResponse<MessageResponse> updateContribution(Long id, ContributionRequest contributionRequest) {
        Contribution contribution = contributionRepository.findById(id)
                .orElseThrow(() -> new ContributionException("Contribution not found"));

        FamilyMember familyMember = familyMemberRepository.findById(contributionRequest.getFamilyMemberId())
                .orElseThrow(() -> new ContributionException("Family member not found"));

        ContributionPlan contributionPlan = contributionPlanRepository.findById(contributionRequest.getContributionPlanId())
                .orElseThrow(() -> new ContributionException("Contribution plan not found"));

        contribution.setContributionPlan(contributionPlan);
        contribution.setFamilyMember(familyMember);
        contribution.setDate(contributionRequest.getDate());

        contributionRepository.save(contribution);

        return new ApiResponse<>(true, "Contribution updated successfully", new MessageResponse("Contribution updated successfully!"));
    }


    public ApiResponse<MessageResponse> deleteContribution(Long id)
    {
        contributionRepository.findById(id)
                .orElseThrow(() -> new ContributionException("Contribution not found"));

        contributionRepository.deleteById(id);

        return new ApiResponse<>(true, "Contribution deleted successfully", new MessageResponse("Contribution deleted successfully!"));
    }
}
