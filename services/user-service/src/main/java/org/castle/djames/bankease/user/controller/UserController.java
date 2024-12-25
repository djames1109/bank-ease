package org.castle.djames.bankease.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.dto.UpdateUserRequest;
import org.castle.djames.bankease.user.dto.UserResponse;
import org.castle.djames.bankease.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    /**
     * Registers a new user based on the provided request data.
     *
     * @param request the {@link RegisterUserRequest} containing user details such as username, password, email,
     *                first name, last name, and role
     * @return a {@link ResponseEntity} containing the {@link UserResponse} with the details of the newly registered user
     */
    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        log.info("Registering user: {}", request);
        var response = userService.registerUser(request);
        log.info("Register user response: {}", response);

        return ResponseEntity.ok(response);
    }

    // todo: must be paginated and be able to sort and filter by status, date created, first name, lastname, username, email and role
    @GetMapping
    public ResponseEntity<List<UserResponse>> getListOfUsers() {
        return null;
    }

    /**
     * Retrieves user details by their username.
     *
     * @param username the username of the user to be retrieved
     * @return a ResponseEntity containing the UserResponse of the user if found
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        log.info("Retrieving user by username: {}", username);
        var response = userService.getUserByUsername(username);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponse> updateUserByUsername(@PathVariable String username,
                                                             @RequestBody @Valid UpdateUserRequest request) {
        log.info("Updating user by username: {}, {}", username, request);
        var response = userService.updateUserByUsername(username, request);
        log.info("Update user response: {}", response);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        
        return null;
    }

}
