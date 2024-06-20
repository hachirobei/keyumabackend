package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.models.ContributionPlan;
import com.keyuma.managementsystem.models.Family;
import com.keyuma.managementsystem.payload.request.ContributionPlanRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.service.ContributionPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contributionPlan")
public class ContributionPlanController {

    @Autowired
    ContributionPlanService contributionPlanService;

    private static final Logger logger = LoggerFactory.getLogger(ContributionPlanController.class);

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR)")
    public ResponseEntity<ApiResponse<PagedApiResponse<ContributionPlan,ContributionPlanDTO>>>  allAccess(@RequestParam(defaultValue = "0") int page,
                                                                                                          @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching all contribution plan with pagination: page = {}, size = {}", page, size);
        try {
            PagedApiResponse<ContributionPlan, ContributionPlanDTO> response = contributionPlanService.getAllContributionPlan(page, size);
            logger.debug("Successfully fetched contribution plan ");
            return ResponseEntity.ok(new ApiResponse<>(true, "All contribution plan retrieved successfully", response));
        } catch (Exception e) {
            logger.error("Error fetching contribution plan: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR) or hasRole('USER')")
    public ResponseEntity<ApiResponse<ContributionPlanDTO>> getContributionPlanById(@PathVariable Long id){
        logger.debug("Fetching contribution plan with ID: {}", id);
        try {
            ContributionPlanDTO contributionPlanDTO = contributionPlanService.getContributionPlanById(id).getData();
            logger.debug("Successfully fetched contribution plan with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Contribution retrieved successfully", contributionPlanDTO));
        } catch (Exception e) {
            logger.error("Error fetching contribution plan with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR)")
    public ResponseEntity<ApiResponse<MessageResponse>> addContributionPlan(@RequestBody ContributionPlanRequest contributionPlanRequest){
        logger.debug("Adding contribution plan with name:{}",contributionPlanRequest.getName());
        try{
            ApiResponse<MessageResponse> response = contributionPlanService.addedContributionPlan(contributionPlanRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("Error adding contribution plan :{}",e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"An unexpected error occurred",null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR)")
    public ResponseEntity<ApiResponse<MessageResponse>> updateContributionPlan(@PathVariable Long id, @RequestBody ContributionPlanRequest contributionPlanRequest){
        logger.debug("Update contribution plan with name:{}",contributionPlanRequest.getName());
        try{
            ApiResponse<MessageResponse> response = contributionPlanService.updateContributionPlan(id,contributionPlanRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("Error updating contribution plan :{}",e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"An unexpected error occurred",null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteContributionPlan(@PathVariable Long id){
        logger.debug("Fetching contribution with ID: {}", id);
        try{
            ApiResponse<MessageResponse> response = contributionPlanService.deleteContributionPlan(id);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("Error deleted contribution plan :{}",e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"An unexpected error occurred",null));
        }
    }

}
