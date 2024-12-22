package org.castle.djames.bankease.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.dto.UserResponse;
import org.castle.djames.bankease.user.entity.User;
import org.castle.djames.bankease.user.exception.UserNotFoundException;
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

        return mapToUserResponse(savedUser);
    }

    public UserResponse getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::mapToUserResponse)
                .orElseThrow(() -> new UserNotFoundException(String.format("User '%s' not found", username)));
    }

    //    ===============>>>>> HELPER METHODS <<<<<====================
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

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .isActive(user.isActive())
                .build();
    }
}
