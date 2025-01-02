package org.castle.djames.bankease.user.it;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        ResponseEntity<UserResponse> response = testRestTemplate.postForEntity("/v1/users", request, UserResponse.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserResponse userResponse = response.getBody();
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

        ResponseEntity<UserResponse> response = testRestTemplate.postForEntity("/v1/users", request, UserResponse.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testSearchUsers_shouldFetchUserDetailsSuccessfully() {
        saveNewUser();

        var response = testRestTemplate.exchange("/v1/users?search=username:johndoe",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<UserResponse>>() {
                });

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).isNotEmpty();
        Assertions.assertThat(response.getBody()).hasSize(1);
        Assertions.assertThat(response.getBody().getFirst().getUsername()).isEqualTo("johndoe");
        Assertions.assertThat(response.getBody().getFirst().getEmail()).isEqualTo("johndoe@domain.com");
        Assertions.assertThat(response.getBody().getFirst().getFirstName()).isEqualTo("John");
        Assertions.assertThat(response.getBody().getFirst().getLastName()).isEqualTo("Doe");
        Assertions.assertThat(response.getBody().getFirst().getRole()).isEqualTo(Role.USER);
        Assertions.assertThat(response.getBody().getFirst().isActive()).isTrue();
    }

    @Test
    void shouldFailToFetchUserDetailsWithInvalidId() {

    }

    @Test
    void shouldUpdateUserDetailsSuccessfully() {

    }

    @Test
    void shouldFailToUpdateUserDetailsWithInvalidData() {

    }

    @Test
    void shouldDeleteUserSuccessfully() {

    }

    @Test
    void shouldFailToDeleteUserWithInvalidId() {

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
