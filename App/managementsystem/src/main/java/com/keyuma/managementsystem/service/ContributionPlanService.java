package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.ContributionPlanException;
import com.keyuma.managementsystem.models.ContributionPlan;
import com.keyuma.managementsystem.payload.request.ContributionPlanRequest;
import com.keyuma.managementsystem.payload.request.ContributionRequest;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.repository.ContributionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContributionPlanService {

    @Autowired
    ContributionPlanRepository contributionPlanRepository;

    public ApiResponse<MessageResponse> addedContributionPlan(ContributionPlanRequest contributionPlanRequest){

        if(contributionPlanRepository.existByName(contributionPlanRequest.getName())){
            return new ApiResponse<>(false, "Error: Name is already taken!", null);
        }

        if(contributionPlanRepository.existByYear(contributionPlanRequest.getYear())){
            return new ApiResponse<>(false, "Error: Year is already taken!", null);
        }

        ContributionPlan contributionPlan = new ContributionPlan(
          contributionPlanRequest.getName(),
          contributionPlanRequest.getYear(),
          contributionPlanRequest.getDefaultMonthlyAmount(),
          contributionPlanRequest.getDefaultYearlyAmount(),
          contributionPlanRequest.getEndDate(),
          contributionPlanRequest.getEndDate()
        );

        contributionPlanRepository.save(contributionPlan);

        return new ApiResponse<>(true, "Contribution Plan adding successfully", new MessageResponse("Contribution Plan adding successfully"));
    }

    public ApiResponse<MessageResponse> updateContributionPlan(Long id , ContributionPlanRequest contributionPlanRequest){
        ContributionPlan contributionPlan = contributionPlanRepository.findById(id)
                .orElseThrow(()-> new ContributionPlanException("Contribution Plan not found"));

        contributionPlan.setName(contributionPlanRequest.getName());
        contributionPlan.setYear(contributionPlanRequest.getYear());
        contributionPlan.setDefaultMonthlyAmount(contributionPlanRequest.getDefaultMonthlyAmount());
        contributionPlan.setDefaultYearlyAmount(contributionPlanRequest.getDefaultYearlyAmount());
        contributionPlan.setStartDate(contributionPlanRequest.getStartDate());
        contributionPlan.setEndDate(contributionPlanRequest.getEndDate());

        contributionPlanRepository.save(contributionPlan);

        return new ApiResponse<>(true, "Contribution Plan updating successfully",new MessageResponse("Contribution Plan updating successfully"));
    }

    public ApiResponse<MessageResponse> deleteContributionPlan(Long id){
        contributionPlanRepository.findById(id)
                .orElseThrow(() -> new ContributionPlanException("Contribution Plan not found"));

        contributionPlanRepository.deleteById(id);

        return new ApiResponse<>(true,"Contribution Plan deleted successfully",new MessageResponse("Contribution Plan deleted succesfully"));
    }
}
