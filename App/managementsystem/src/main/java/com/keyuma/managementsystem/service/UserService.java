package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.UserException;
import com.keyuma.managementsystem.models.ERole;
import com.keyuma.managementsystem.models.Role;
import com.keyuma.managementsystem.models.User;
import com.keyuma.managementsystem.payload.request.UserRequest;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.payload.response.PagedApiResponse;
import com.keyuma.managementsystem.payload.response.UserDTO;
import com.keyuma.managementsystem.repository.RoleRepository;
import com.keyuma.managementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public PagedApiResponse<User, UserDTO> getAllUsers(int page, int size) {
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

        return new PagedApiResponse<>(true, "All users retrieved successfully", userPage, userDTOs);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new UserException("User not found!"));

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        );
    }

    public ApiResponse<MessageResponse> addUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return new ApiResponse<>(false, "Error: Username is already taken!", null);
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return new ApiResponse<>(false, "Error: Email is already in use!", null);
        }

        User user = new User(userRequest.getUsername(), userRequest.getEmail(),
                encoder.encode(userRequest.getPassword()));

        Set<String> strRoles = userRequest.getRoles();
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

        return new ApiResponse<>(true, "User added successfully", new MessageResponse("User added successfully!"));
    }

    public ApiResponse<MessageResponse> updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new UserException("User not found!"));

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);

        return new ApiResponse<>(true, "User updated successfully", new MessageResponse("User updated successfully!"));
    }

    public ApiResponse<MessageResponse> deleteUser(Long id) {
        userRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new UserException("User not found!"));

        userRepository.deleteById(id);
        return new ApiResponse<>(true, "User deleted successfully", new MessageResponse("User deleted successfully!"));
    }
}
