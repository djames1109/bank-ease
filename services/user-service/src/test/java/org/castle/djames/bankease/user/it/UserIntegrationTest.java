package org.castle.djames.bankease.user.it;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.castle.djames.bankease.common.model.Response;
import org.castle.djames.bankease.common.model.ResponseStatus;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.dto.UpdateUserRequest;
import org.castle.djames.bankease.user.dto.UserResponse;
import org.castle.djames.bankease.user.entity.Role;
import org.castle.djames.bankease.user.entity.User;
import org.castle.djames.bankease.user.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.List;

@Slf4j
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @Container
    @ServiceConnection(name = "postgres")
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine3.19")
            .withStartupTimeout(Duration.ofSeconds(90))
            .withInitScript("init.sql");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setupClass() {
        postgres.start();
    }

    @AfterAll
    static void teardownClass() {
        postgres.stop();
    }

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

//    ============>>>>> TESTS <<<<<=============

    @Test
    void testRegisterUser_shouldCreateUserSuccessfully() {
        var request = new RegisterUserRequest("johndoe", "password", "johndoe@domain.com", "John", "Doe", Role.USER);

        var response = testRestTemplate.exchange("/v1/users", HttpMethod.POST, new HttpEntity<>(request), new ParameterizedTypeReference<Response<UserResponse>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var responseWrapper = response.getBody();
        Assertions.assertThat(responseWrapper).isNotNull();
        Assertions.assertThat(responseWrapper.getStatus()).isEqualTo(ResponseStatus.SUCCESS);
        Assertions.assertThat(responseWrapper.getCode()).isEqualTo("US_S001");
        Assertions.assertThat(responseWrapper.getMessage()).isEqualTo("Successfully registered user.");

        UserResponse userResponse = responseWrapper.getBody();
        Assertions.assertThat(userResponse).isNotNull();
        Assertions.assertThat(userResponse.getUsername()).isEqualTo("johndoe");
        Assertions.assertThat(userResponse.getEmail()).isEqualTo("johndoe@domain.com");
        Assertions.assertThat(userResponse.getFirstName()).isEqualTo("John");
        Assertions.assertThat(userResponse.getLastName()).isEqualTo("Doe");
        Assertions.assertThat(userResponse.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void testRegisterUser_shouldFailToCreateUserWithInvalidData() {
        var request = new RegisterUserRequest("johndoe", "password", "johndoe", "John", "Doe", Role.USER);
        var response = testRestTemplate.exchange("/v1/users", HttpMethod.POST, new HttpEntity<>(request), new ParameterizedTypeReference<Void>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        //todo: add more field assertions here.
    }

    @Test
    void testGetUserByUsername_shouldFetchUserDetailsSuccessfully() {
        saveNewUser();

        var response = testRestTemplate.exchange("/v1/users/johndoe", HttpMethod.GET, null, new ParameterizedTypeReference<Response<UserResponse>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var responseWrapper = response.getBody();
        Assertions.assertThat(responseWrapper).isNotNull();
        Assertions.assertThat(responseWrapper.getStatus()).isEqualTo(ResponseStatus.SUCCESS);
        Assertions.assertThat(responseWrapper.getCode()).isEqualTo("US_S002");
        Assertions.assertThat(responseWrapper.getMessage()).isEqualTo("Successfully retrieved user.");

        UserResponse userResponse = responseWrapper.getBody();
        Assertions.assertThat(userResponse).isNotNull();
        Assertions.assertThat(userResponse.getUsername()).isEqualTo("johndoe");
        Assertions.assertThat(userResponse.getEmail()).isEqualTo("johndoe@domain.com");
        Assertions.assertThat(userResponse.getFirstName()).isEqualTo("John");
        Assertions.assertThat(userResponse.getLastName()).isEqualTo("Doe");
        Assertions.assertThat(userResponse.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void testGetUserByUsername_shouldFailToFetchUserDetailsWithInvalidUsername() {
        var response = testRestTemplate.exchange("/v1/users/johndoe", HttpMethod.GET, null, new ParameterizedTypeReference<Response<UserResponse>>() {
        });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        var responseWrapper = response.getBody();
        Assertions.assertThat(responseWrapper).isNotNull();
        Assertions.assertThat(responseWrapper.getStatus()).isEqualTo(ResponseStatus.ERROR);
        Assertions.assertThat(responseWrapper.getCode()).isEqualTo("BE_US_E001");
        Assertions.assertThat(responseWrapper.getMessage()).isEqualTo("User with username 'johndoe' not found");
        Assertions.assertThat(responseWrapper.getBody()).isNull();

    }

    //todo: Add test to get user by user name that is not existing, return 404


    @Test
    void testSearchUsers_shouldFetchUserDetailsSuccessfully() {
        saveNewUser();

        var response = testRestTemplate.exchange("/v1/users?search=username:johndoe",
                HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<UserResponse>>>() {
                });
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var responseWrapper = response.getBody();
        Assertions.assertThat(responseWrapper).isNotNull();
        Assertions.assertThat(responseWrapper.getStatus()).isEqualTo(ResponseStatus.SUCCESS);
        Assertions.assertThat(responseWrapper.getCode()).isEqualTo("US_S003");
        Assertions.assertThat(responseWrapper.getMessage()).isEqualTo("Successfully retrieved users.");

        var users = responseWrapper.getBody();
        Assertions.assertThat(users).isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(users.getFirst().getUsername()).isEqualTo("johndoe");
        Assertions.assertThat(users.getFirst().getEmail()).isEqualTo("johndoe@domain.com");
        Assertions.assertThat(users.getFirst().getFirstName()).isEqualTo("John");
        Assertions.assertThat(users.getFirst().getLastName()).isEqualTo("Doe");
        Assertions.assertThat(users.getFirst().getRole()).isEqualTo(Role.USER);
        Assertions.assertThat(users.getFirst().isActive()).isTrue();
    }

    //todo: Add test to search users. if none found, return empty instead of an error

    @Test
    void updateUserByUsername_shouldUpdateUserDetailsSuccessfully() {
        saveNewUser();

        var body = new UpdateUserRequest(null, "johndoe2@gmail.com", null, null, null, null);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(body, headers);

        var response = testRestTemplate.exchange("/v1/users/johndoe", HttpMethod.PUT, request, new ParameterizedTypeReference<Response<UserResponse>>() {
        });


        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();

        var responseWrapper = response.getBody();
        Assertions.assertThat(responseWrapper).isNotNull();
        Assertions.assertThat(responseWrapper.getStatus()).isEqualTo(ResponseStatus.SUCCESS);
        Assertions.assertThat(responseWrapper.getCode()).isEqualTo("US_S004");
        Assertions.assertThat(responseWrapper.getMessage()).isEqualTo("Successfully updated user.");

        var userResponse = responseWrapper.getBody();
        Assertions.assertThat(userResponse).isNotNull();
        Assertions.assertThat(userResponse.getUsername()).isEqualTo("johndoe");
        Assertions.assertThat(userResponse.getEmail()).isEqualTo("johndoe2@gmail.com");
        Assertions.assertThat(userResponse.getFirstName()).isEqualTo("John");
        Assertions.assertThat(userResponse.getLastName()).isEqualTo("Doe");
        Assertions.assertThat(userResponse.getRole()).isEqualTo(Role.USER);
        Assertions.assertThat(userResponse.isActive()).isTrue();

    }

    @Test
    void updateUserByUsername_shouldFailToUpdateUserDetailsWithInvalidData() {
        saveNewUser();

        var body = new UpdateUserRequest(null, "testEmail", null, null, null, null);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(body, headers);

        var response = testRestTemplate.exchange("/v1/users/johndoe", HttpMethod.PUT, request, UserResponse.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        saveNewUser();

//        verify if user is existing
        var verifyUserResponse = testRestTemplate.exchange("/v1/users/johndoe", HttpMethod.GET, null, new ParameterizedTypeReference<Response<UserResponse>>() {
        });
        Assertions.assertThat(verifyUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(verifyUserResponse.getBody()).isNotNull();
        Assertions.assertThat(verifyUserResponse.getBody().getBody()).isNotNull();
        Assertions.assertThat(verifyUserResponse.getBody().getBody().getUsername()).isEqualTo("johndoe");

//        execute delete command
        var response = testRestTemplate.exchange("/v1/users/johndoe", HttpMethod.DELETE, null, new ParameterizedTypeReference<Response<Object>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var responseWrapper = response.getBody();
        Assertions.assertThat(responseWrapper).isNotNull();
        Assertions.assertThat(responseWrapper.getStatus()).isEqualTo(ResponseStatus.SUCCESS);
        Assertions.assertThat(responseWrapper.getCode()).isEqualTo("US_S005");
        Assertions.assertThat(responseWrapper.getMessage()).isEqualTo("Successfully deleted user.");
        Assertions.assertThat(responseWrapper.getBody()).isNull();

//        verify if user is deleted
//        var deletedUser = testRestTemplate.getForEntity("/v1/users/johndoe", UserResponse.class);
//        Assertions.assertThat(deletedUser.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private User saveNewUser() {
        var user = buildUser();
        return userRepository.save(user);
    }

    private User buildUser() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .password(new BCryptPasswordEncoder().encode("password"))
                .email("johndoe@domain.com")
                .active(true)
                .role(Role.USER)
                .build();
    }


}
