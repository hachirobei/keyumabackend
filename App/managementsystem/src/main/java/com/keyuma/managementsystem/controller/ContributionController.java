package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.models.Contribution;
import com.keyuma.managementsystem.models.Family;
import com.keyuma.managementsystem.payload.request.ContributionRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.service.ContributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contribution")
public class ContributionController {

    @Autowired
    private ContributionService contributionService;

    private static final Logger logger = LoggerFactory.getLogger(ContributionController.class);

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<PagedApiResponse<Contribution, ContributionDTO>>> allAccess(@RequestParam(defaultValue = "0") int page,
                                                                                                  @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching all contribution with pagination: page = {}, size = {}", page, size);
        try {
            PagedApiResponse<Contribution, ContributionDTO> response = contributionService.getAllContribution(page, size);
            logger.debug("Successfully fetched contribution ");
            return ResponseEntity.ok(new ApiResponse<>(true, "All contribution retrieved successfully", response));
        } catch (Exception e) {
            logger.error("Error fetching contribution: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<ContributionDTO>> getContributionById(@PathVariable  Long id){
        logger.debug("Fetching user with ID: {}", id);
        try {
            ContributionDTO contributionDTO = contributionService.getContributionById(id).getData();
            logger.debug("Successfully fetched contribution with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Contribution retrieved successfully", contributionDTO));
        } catch (Exception e) {
            logger.error("Error fetching contribution with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<MessageResponse>> addContribution(@RequestBody ContributionRequest contributionRequest){
        logger.debug("Adding contribution plan id for contribution:{}",contributionRequest.getContributionPlanId());
        logger.debug("Adding family member id for contribution:{}",contributionRequest.getFamilyMemberId());
        try{
            ApiResponse<MessageResponse> response = contributionService.addContribution(contributionRequest);
            return  ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("Error adding contribution: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<MessageResponse>> updateContributionById(@PathVariable Long id , @RequestBody ContributionRequest contributionRequest){
        logger.debug("Updating contribution with ID: {}", id);
        try {
            ApiResponse<MessageResponse> response = contributionService.updateContribution(id, contributionRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteContributionById(@PathVariable Long id){
        logger.debug("Delete contribution with ID: {}", id);
        try {
            ApiResponse<MessageResponse> response = contributionService.deleteContribution(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error deleted user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

}
