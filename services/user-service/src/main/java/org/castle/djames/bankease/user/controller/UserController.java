package org.castle.djames.bankease.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.common.model.Response;
import org.castle.djames.bankease.common.util.GenericResponse;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.dto.UpdateUserRequest;
import org.castle.djames.bankease.user.dto.UserResponse;
import org.castle.djames.bankease.user.service.UserService;
import org.springframework.data.domain.Pageable;
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
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<UserResponse>> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        log.info("Registering user: {}", request);

        var body = userService.registerUser(request);
        var response = GenericResponse.success(body, "US_S001", "Successfully registered user.");

        log.info("Register user response: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves user details by their username.
     *
     * @param username the username of the user to be retrieved
     * @return a ResponseEntity containing the UserResponse of the user if found
     */
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<Response<UserResponse>> getUserByUsername(@PathVariable String username) {
        log.info("Retrieving user by username: {}", username);

        var body = userService.getUserByUsername(username);
        var response = GenericResponse.success(body, "US_S002", "Successfully retrieved user.");

        return ResponseEntity.ok(response);
    }

    /**
     * Searches for users based on the provided search parameter.
     *
     * @param search the search term used to filter users, such as username, email, or name
     * @return a {@link ResponseEntity} containing a list of {@link UserResponse} that matches the search criteria
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<Response<List<UserResponse>>> searchUsers(@RequestParam(value = "search") String search, Pageable pageRequest) {
        log.info("Searching users with search parameters: {}", search);

        var body = userService.search(search, pageRequest);
        var response = GenericResponse.success(body, "US_S003", "Successfully retrieved users.");

        log.info("Search users response: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Updates the details of a user identified by their username.
     *
     * @param username the username of the user to be updated
     * @param request  the {@link UpdateUserRequest} containing updated details of the user
     * @return a {@link ResponseEntity} containing the updated {@link UserResponse} of the user
     */
    @PutMapping(value = "/{username}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<UserResponse>> updateUserByUsername(@PathVariable String username,
                                                                       @RequestBody @Valid UpdateUserRequest request) {
        log.info("Updating user by username: {}, {}", username, request);

        var body = userService.updateUserByUsername(username, request);
        var response = GenericResponse.success(body, "US_S004", "Successfully updated user.");

        log.info("Update user response: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a user identified by their username.
     *
     * @param username the username of the user to be deleted
     * @return a {@link ResponseEntity} with no content upon successful deletion
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Response<Object>> deleteUserByUsername(@PathVariable String username) {
        log.info("Deleting user by username: {}", username);

        userService.deleteUserByUsername(username);
        var response = GenericResponse.success(null, "US_S005", "Successfully deleted user.");

        log.info("Delete user response: {}", response);

        return ResponseEntity.ok(response);
    }

}
