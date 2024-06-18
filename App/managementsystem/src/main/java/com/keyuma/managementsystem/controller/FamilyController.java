package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.service.FamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteFamily(@PathVariable Long id) {
        logger.debug("Deleting family with ID: {}", id);
        try {
            ApiResponse<MessageResponse> response = familyService.deleteFamily(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error deleting family with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

}
