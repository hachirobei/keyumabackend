package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.ContributionException;
import com.keyuma.managementsystem.models.Contribution;
import com.keyuma.managementsystem.models.ContributionPlan;
import com.keyuma.managementsystem.models.FamilyMember;
import com.keyuma.managementsystem.payload.request.ContributionRequest;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.repository.ContributionPlanRepository;
import com.keyuma.managementsystem.repository.ContributionRepository;
import com.keyuma.managementsystem.repository.FamilyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContributionService {

    @Autowired
    ContributionRepository contributionRepository;

    @Autowired
    ContributionPlanRepository contributionPlanRepository;

    @Autowired
    FamilyMemberRepository familyMemberRepository;

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
