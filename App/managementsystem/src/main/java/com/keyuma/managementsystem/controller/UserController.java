package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.models.User;
import com.keyuma.managementsystem.payload.request.UserRequest;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.payload.response.PagedApiResponse;
import com.keyuma.managementsystem.payload.response.UserDTO;
import com.keyuma.managementsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagedApiResponse<User, UserDTO>>> allAccess(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching all users with pagination: page = {}, size = {}", page, size);
        try {
            PagedApiResponse<User, UserDTO> response = userService.getAllUsers(page, size);
            logger.debug("Successfully fetched users");
            return ResponseEntity.ok(new ApiResponse<>(true, "All users retrieved successfully", response));
        } catch (Exception e) {
            logger.error("Error fetching users: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        logger.debug("Fetching user with ID: {}", id);
        try {
            UserDTO userDTO = userService.getUserById(id);
            logger.debug("Successfully fetched user with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", userDTO));
        } catch (Exception e) {
            logger.error("Error fetching user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<MessageResponse>> addUser(@RequestBody UserRequest userRequest) {
        logger.debug("Adding new user with username: {}", userRequest.getUsername());
        try {
            ApiResponse<MessageResponse> response = userService.addUser(userRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error adding user: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<MessageResponse>> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        logger.debug("Updating user with ID: {}", id);
        try {
            ApiResponse<MessageResponse> response = userService.updateUser(id, userRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MessageResponse>> deleteUser(@PathVariable Long id) {
        logger.debug("Deleting user with ID: {}", id);
        try {
            ApiResponse<MessageResponse> response = userService.deleteUser(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> userAccess() {
        return ResponseEntity.ok(new ApiResponse<>(true, "User access", "User Content."));
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<String>> moderatorAccess() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Moderator access", "Moderator Board."));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> adminAccess() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Admin access", "Admin Board."));
    }
}
