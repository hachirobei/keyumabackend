package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.models.Family;
import com.keyuma.managementsystem.payload.request.FamilyRequest;
import com.keyuma.managementsystem.payload.response.*;
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagedApiResponse<Family, FamilyDTO>>> allAccess(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching all families with pagination: page = {}, size = {}", page, size);
        try {
            PagedApiResponse<Family, FamilyDTO> response = familyService.getAllFamily(page, size);
            logger.debug("Successfully fetched families ");
            return ResponseEntity.ok(new ApiResponse<>(true, "All families retrieved successfully", response));
        } catch (Exception e) {
            logger.error("Error fetching families: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FamilyDTO>> getUserById(@PathVariable Long id) {
        logger.debug("Fetching family with ID: {}", id);
        try {
            FamilyDTO familyDTO = familyService.getFamilyById(id);
            logger.debug("Successfully fetched family with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", familyDTO));
        } catch (Exception e) {
            logger.error("Error fetching family with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<ApiResponse<MessageResponse>> addFamily(@RequestBody FamilyRequest familyRequest){
        logger.debug("Adding family with ID:{}",familyRequest.getName());
        try{
            ApiResponse<MessageResponse> response = familyService.addFamily(familyRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("Error adding family :{}",e.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"An unexpected error occurred",null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<MessageResponse>> updateFamily(@PathVariable Long id, @RequestBody FamilyRequest familyRequest){
        logger.debug("Updating family with ID:{}",id);
        try{
            ApiResponse<MessageResponse> response = familyService.updateFamily(id,familyRequest);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("Error updating family with ID {}:{}",id,e.getMessage());
            return  ResponseEntity.status(500).body(new ApiResponse<>(false,"An unexpected error occurred",null));
        }
    }

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
