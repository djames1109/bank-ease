package org.castle.djames.bankease.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.dto.UserResponse;
import org.castle.djames.bankease.user.entity.User;
import org.castle.djames.bankease.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse registerUser(RegisterUserRequest request) {
        var user = buildUser(request);
        var savedUser = userRepository.save(user);

        return UserResponse.builder()
                .username(savedUser.getUsername())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .isActive(savedUser.isActive())
                .build();
    }

    private User buildUser(RegisterUserRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
    }
}
