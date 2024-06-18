package com.keyuma.managementsystem.service;

import com.keyuma.managementsystem.exception.UserException;
import com.keyuma.managementsystem.payload.response.ApiResponse;
import com.keyuma.managementsystem.payload.response.MessageResponse;
import com.keyuma.managementsystem.repository.FamilyRepository;
import com.keyuma.managementsystem.repository.RoleRepository;
import com.keyuma.managementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class FamilyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FamilyRepository familyRepository;

    public ApiResponse<MessageResponse> deleteFamily(Long id) {
        familyRepository.findById(id)
                .orElseThrow(() -> new UserException("Family not found!"));

        familyRepository.deleteById(id);
        return new ApiResponse<>(true, "Family deleted successfully", new MessageResponse("Family deleted successfully!"));
    }
}
