package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.exception.UserException;
import com.keyuma.managementsystem.models.ERole;
import com.keyuma.managementsystem.models.Role;
import com.keyuma.managementsystem.models.User;
import com.keyuma.managementsystem.payload.request.UpdateUserRequest;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.payload.response.PagedApiResponse;
import com.keyuma.managementsystem.payload.response.UserDTO;
import com.keyuma.managementsystem.repository.RoleRepository;
import com.keyuma.managementsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PagedApiResponse<User, UserDTO>>> allAccess(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        logger.debug("Fetching all users with pagination: page = {}, size = {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage = userRepository.findAllWithRoles(pageable);

            List<UserDTO> userDTOs = userPage.getContent().stream()
                    .map(user -> new UserDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getRoles().stream()
                                    .map(role -> role.getName().name())
                                    .collect(Collectors.toSet())
                    ))
                    .collect(Collectors.toList());

            logger.debug("Successfully fetched {} users", userDTOs.size());
            return ResponseEntity.ok(new ApiResponse<>(true, "All users retrieved successfully", new PagedApiResponse<>(true, "All users retrieved successfully", userPage, userDTOs)));
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
            User user = userRepository.findByIdWithRoles(id)
                    .orElseThrow(() -> new UserException("User not found!"));

            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRoles().stream()
                            .map(role -> role.getName().name())
                            .collect(Collectors.toSet())
            );

            logger.debug("Successfully fetched user with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", userDTO));
        } catch (UserException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(404).body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            logger.error("Error fetching user with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<MessageResponse>> addUser(@RequestBody UpdateUserRequest updateUserRequest) {
        logger.debug("Adding new user with username: {}", updateUserRequest.getUsername());
        try {
            if (userRepository.existsByUsername(updateUserRequest.getUsername())) {
                logger.warn("Username is already taken: {}", updateUserRequest.getUsername());
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error: Username is already taken!", null));
            }

            if (userRepository.existsByEmail(updateUserRequest.getEmail())) {
                logger.warn("Email is already in use: {}", updateUserRequest.getEmail());
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error: Email is already in use!", null));
            }

            User user = new User(updateUserRequest.getUsername(), updateUserRequest.getEmail(),
                    encoder.encode(updateUserRequest.getPassword()));

            Set<String> strRoles = updateUserRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;
                        case "mod":
                            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            userRepository.save(user);

            logger.debug("User added successfully with username: {}", updateUserRequest.getUsername());
            return ResponseEntity.ok(new ApiResponse<>(true, "User added successfully", new MessageResponse("User added successfully!")));
        } catch (Exception e) {
            logger.error("Error adding user: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "An unexpected error occurred", null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse<MessageResponse>> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) {
        logger.debug("Updating user with ID: {}", id);
        try {
            User user = userRepository.findByIdWithRoles(id)
                    .orElseThrow(() -> new UserException("User not found!"));

            user.setUsername(updateUserRequest.getUsername());
            user.setEmail(updateUserRequest.getEmail());
            userRepository.save(user);

            logger.debug("User updated successfully with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", new MessageResponse("User updated successfully!")));
        } catch (UserException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(404).body(new ApiResponse<>(false, e.getMessage(), null));
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
            userRepository.findByIdWithRoles(id)
                    .orElseThrow(() -> new UserException("User not found!"));

            userRepository.deleteById(id);
            logger.debug("User deleted successfully with ID: {}", id);
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", new MessageResponse("User deleted successfully!")));
        } catch (UserException e) {
            logger.error("User not found: {}", e.getMessage());
            return ResponseEntity.status(404).body(new ApiResponse<>(false, e.getMessage(), null));
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
