package org.castle.djames.bankease.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping
    public ResponseEntity<UserResponse> registerUser() {

        return null;
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
        return null;
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponse> updateUserByUsername(@PathVariable String username) {
        return null;
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<UserResponse> deleteUserByUsername(@PathVariable String username) {
        return null;
    }

}
