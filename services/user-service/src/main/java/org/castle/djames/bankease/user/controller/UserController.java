package org.castle.djames.bankease.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.dto.UpdateUserRequest;
import org.castle.djames.bankease.user.dto.UserResponse;
import org.castle.djames.bankease.user.entity.User;
import org.castle.djames.bankease.user.repository.UserRepository;
import org.castle.djames.bankease.user.service.UserService;
import org.castle.djames.bankease.user.specification.SearchOperation;
import org.castle.djames.bankease.user.specification.UserSpecificationsBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserController {

    private final UserRepository repo;
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

    @GetMapping("/search")
    public ResponseEntity<List<User>> getListOfUsers(@RequestParam(value = "search") String search) {
        log.info("Searching users with search parameters: {}", search);
        Specification<User> spec = resolveSpecification(search);
        var response = repo.findAll(spec);
        log.info("Search users response: {}", response);


        return ResponseEntity.ok(response);
    }

    protected Specification<User> resolveSpecification(String searchParameters) {

        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        String operationSetExper = String.join("|", SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(searchParameters + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
        }
        return builder.build();
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
        log.info("Deleting user by username: {}", username);
        userService.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }

}
