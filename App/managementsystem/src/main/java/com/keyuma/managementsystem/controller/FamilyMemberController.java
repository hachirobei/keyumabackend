package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.models.FamilyMember;
import com.keyuma.managementsystem.payload.request.FamilyMemberRequest;
import com.keyuma.managementsystem.payload.response.*;
import com.keyuma.managementsystem.service.FamilyMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/family_member")
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService familyMemberService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagedApiResponse<FamilyMember, FamilyMemberDTO>>> getAllFamilyMember(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching all family members with pagination: page = {}, size = {}", page, size);
        try {
            PagedApiResponse<FamilyMember, FamilyMemberDTO> response = familyMemberService.getAllFamilyMember(page, size);
            logger.debug("Successfully fetched family members");
            return ResponseEntity.ok(new ApiResponse<>(true, "All family members retrieved successfully", response));
        } catch (Exception e) {
            logger.error("Error fetching family members: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FamilyMemberDTO>> getUserById(@PathVariable Long id) {
        logger.debug("Fetching family with ID: {}", id);
        try {
            FamilyMemberDTO familyMemberDTO = familyMemberService.getFamilyMemberById(id);
            logger.debug("Successfully fetched family member with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Family retrieved successfully", familyMemberDTO));
        } catch (Exception e) {
            logger.error("Error fetching family with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<ApiResponse<MessageResponse>> addFamilyMember(@RequestBody FamilyMemberRequest familyMemberRequest){
        logger.debug("Adding family member firstname:{}",familyMemberRequest.getFirstName());
        try{
            ApiResponse<MessageResponse> response = familyMemberService.addFamilyMember(familyMemberRequest);
            return  ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("Error adding family member: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<MessageResponse>> updateFamilyMember(@PathVariable Long id,@RequestBody FamilyMemberRequest familyMemberRequest){
        logger.debug("Updating family member with ID: {}",id);
        try{
            ApiResponse<MessageResponse> response = familyMemberService.updateFamilyMember(id, familyMemberRequest);
            return ResponseEntity.ok(response);

        }catch(Exception e){
            logger.error("Error updating family member with ID {}: {}",id,e.getMessage(),e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"An unexpected error occurred",null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteFamilyMember(@PathVariable Long id){
        logger.debug("Deleting user with ID: {}", id);
        try {
            ApiResponse<MessageResponse> response = familyMemberService.deleteFamilyMember(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }
}
