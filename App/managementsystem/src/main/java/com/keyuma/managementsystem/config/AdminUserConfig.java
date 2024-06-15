package com.keyuma.managementsystem.config;

import com.keyuma.managementsystem.models.ERole;
import com.keyuma.managementsystem.models.Role;
import com.keyuma.managementsystem.models.User;
import com.keyuma.managementsystem.repository.RoleRepository;
import com.keyuma.managementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AdminUserConfig {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Initialize roles
            if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_ADMIN));
            }
            if (roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_MODERATOR));
            }
            if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_USER));
            }

            // Initialize admin user
            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User("admin", "admin@example.com", passwordEncoder.encode("password"));
                Set<Role> roles = new HashSet<>();
                roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
                adminUser.setRoles(roles);
                userRepository.save(adminUser);
            }
        };
    }
}
