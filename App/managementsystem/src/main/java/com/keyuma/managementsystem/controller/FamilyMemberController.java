package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
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
