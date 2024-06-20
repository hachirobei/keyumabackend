package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.ContributionPlanException;
import com.keyuma.managementsystem.models.ContributionPlan;
import com.keyuma.managementsystem.payload.request.ContributionPlanRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.repository.ContributionPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributionPlanService {

    @Autowired
    ContributionPlanRepository contributionPlanRepository;

    public PagedApiResponse<ContributionPlan, ContributionPlanDTO> getAllContributionPlan(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ContributionPlan> ContributionPlanPage = contributionPlanRepository.findAll(pageable);

        List<ContributionPlanDTO> ContributionPlanDTOs = ContributionPlanPage.getContent().stream()
                .map(contributionPlan -> new ContributionPlanDTO(
                        contributionPlan.getId(),
                        contributionPlan.getName(),
                        contributionPlan.getYear(),
                        contributionPlan.getDefaultYearlyAmount(),
                        contributionPlan.getDefaultMonthlyAmount(),
                        contributionPlan.getStartDate(),
                contributionPlan.getEndDate()))
                .collect(Collectors.toList());

        return new PagedApiResponse<>(true, "All transaction retrieved successfully", ContributionPlanPage, ContributionPlanDTOs);
    }

    public ApiResponse<ContributionPlanDTO> getContributionPlanById(Long id){
        ContributionPlan contributionPlan = contributionPlanRepository.findById(id)
                .orElseThrow(()-> new ContributionPlanException("Contribution Plan not found"));

        ContributionPlanDTO contributionPlanDTO = new ContributionPlanDTO(
                contributionPlan.getId(),
                contributionPlan.getName(),
                contributionPlan.getYear(),
                contributionPlan.getDefaultYearlyAmount(),
                contributionPlan.getDefaultMonthlyAmount(),
                contributionPlan.getStartDate(),
                contributionPlan.getEndDate()
        );

        return new ApiResponse<>(true,"Contribution retrive successfully",contributionPlanDTO);
    }

    public ApiResponse<MessageResponse> addedContributionPlan(ContributionPlanRequest contributionPlanRequest){

        if(contributionPlanRepository.existsByName(contributionPlanRequest.getName())){
            return new ApiResponse<>(false, "Error: Name is already taken!", null);
        }

        if(contributionPlanRepository.existsByYear(contributionPlanRequest.getYear())){
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
