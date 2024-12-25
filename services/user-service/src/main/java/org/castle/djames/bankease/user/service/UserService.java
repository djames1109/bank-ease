package org.castle.djames.bankease.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.dto.UpdateUserRequest;
import org.castle.djames.bankease.user.dto.UserResponse;
import org.castle.djames.bankease.user.entity.Role;
import org.castle.djames.bankease.user.entity.User;
import org.castle.djames.bankease.user.exception.UserNotFoundException;
import org.castle.djames.bankease.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;

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
        return mapToUserResponse(getExistingUserByUsername(username));
    }

    @Transactional
    public UserResponse updateUserByUsername(String username, UpdateUserRequest request) {
        log.info("Updating user details: {}", username);

        var existingUser = getExistingUserByUsername(username);

        updateIfExisting(existingUser::setEmail, request.email());
        updateIfExisting(existingUser::setFirstName, request.firstName());
        updateIfExisting(existingUser::setLastName, request.lastName());
        updateIfExisting(existingUser::setRole, request.role());
        updateIfExisting(existingUser::setActive, request.active());

        return mapToUserResponse(existingUser);
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        log.info("Deleting user: {}", username);
        var existingUser = getExistingUserByUsername(username);
        userRepository.delete(existingUser);
    }

    //    ===============>>>>> HELPER METHODS <<<<<====================
    private User getExistingUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username '%s' not found", username)));
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

    private void updateIfExisting(Consumer<String> expression, String value) {
        Optional.of(value).ifPresent(expression);
    }

    private void updateIfExisting(Consumer<Role> expression, Role value) {
        Optional.of(value).ifPresent(expression);
    }

    private void updateIfExisting(Consumer<Boolean> expression, Boolean value) {
        Optional.of(value).ifPresent(expression);
    }
}
