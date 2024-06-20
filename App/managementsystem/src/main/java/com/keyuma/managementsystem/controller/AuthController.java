package com.keyuma.managementsystem.controller;

import com.keyuma.managementsystem.exception.TokenRefreshException;
import com.keyuma.managementsystem.models.ERole;
import com.keyuma.managementsystem.models.RefreshToken;
import com.keyuma.managementsystem.models.Role;
import com.keyuma.managementsystem.models.User;
import com.keyuma.managementsystem.payload.request.LoginRequest;
import com.keyuma.managementsystem.payload.request.SignupRequest;
import com.keyuma.managementsystem.payload.request.TokenRefreshRequest;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.JwtResponse;
import com.keyuma.managementsystem.payload.response.TokenRefreshResponse;
import com.keyuma.managementsystem.repository.RoleRepository;
import com.keyuma.managementsystem.repository.UserRepository;
import com.keyuma.managementsystem.security.jwt.JwtUtils;
import com.keyuma.managementsystem.security.services.RefreshTokenService;
import com.keyuma.managementsystem.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Clear the existing authentication context to allow re-login
            SecurityContextHolder.clearContext();

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Set the authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Log the generated token for debugging purposes
            System.out.println("Generated JWT Token: " + jwt);

            // Create and save refresh token
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String refreshToken = refreshTokenService.createRefreshToken(userDetails.getId()).getToken();

            // Get user roles
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            JwtResponse jwtResponse = new JwtResponse(jwt, refreshToken, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);

            // Return JWT and refresh token in the response
            return ResponseEntity.ok(new ApiResponse<>(true, "User authenticated successfully", jwtResponse));
        }catch (AuthenticationException e) {
            // Return error response for wrong username or password
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Invalid username or password", null));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error: Username is already taken!", null));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error: Email is already in use!", null));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
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

        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", null));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new ApiResponse<>(true, "Token refreshed successfully", new TokenRefreshResponse(token, requestRefreshToken)));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<ApiResponse<Object>> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Log out successful", null));
    }
}
