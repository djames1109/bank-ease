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
import org.castle.djames.bankease.user.specification.SearchOperation;
import org.castle.djames.bankease.user.specification.UserSpecificationsBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public List<UserResponse> search(String searchParam) {
        Specification<User> spec = resolveSearchSpecification(searchParam);
        var response = userRepository.findAll(spec);

        return response.stream().map(this::mapToUserResponse).toList();
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

    /**
     * Resolves and builds a search specification based on input parameters.
     * This method parses the provided search parameters using a regular expression
     * to extract conditions such as field, operator, and value. The extracted
     * conditions are then used to build a Specification<User> via the 
     * UserSpecificationsBuilder.
     * <p> 
     * You can also specify an `orPredicate` by prefixing the key with a single 
     * quote (`'`). For example: "'username:john,email:john@domain.com," would
     * treat "username" as an OR condition while combining it with the rest of 
     * the criteria.
     * <p>
     * Additionally, you can add `*` before or after the value to indicate a wildcard search.
     *
     * @param searchParameters a comma-separated string of search criteria in the format:
     *                         "<field><operator><value>,"
     *                         For example: "username:jon*,email:*@domain.com,"
     * @return a {@link Specification<User>} that can be used to query the database.
     */
    private Specification<User> resolveSearchSpecification(String searchParameters) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        String operationSetExpression = String.join("|", SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?)(" + operationSetExpression + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(searchParameters + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
        }
        return builder.build();
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
