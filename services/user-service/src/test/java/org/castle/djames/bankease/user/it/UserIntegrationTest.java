package org.castle.djames.bankease.user.it;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.dto.UserResponse;
import org.castle.djames.bankease.user.entity.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

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

    @BeforeAll
    static void setupClass() {
        postgres.start();
    }

    @AfterAll
    static void teardownClass() {
        postgres.stop();
    }

//    ============>>>>> TESTS <<<<<=============

    @Test
    void testRegisterUser_shouldCreateUserSuccessfully() {
        var request = new RegisterUserRequest("johndoe", "password", "johndoe@domain.com", "John", "Doe", Role.USER);

        ResponseEntity<UserResponse> response = testRestTemplate.postForEntity("/v1/users", request, UserResponse.class);
        log.info("Response: {}", response);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void shouldFailToCreateUserWithInvalidData() {
        // TODO: Implement test: Attempt creating a user with invalid data and assert failure
    }

    @Test
    void shouldFetchUserDetailsSuccessfully() {
        // TODO: Implement test: Fetch user details using a valid user ID and assert correctness
    }

    @Test
    void shouldFailToFetchUserDetailsWithInvalidId() {
        // TODO: Implement test: Attempt fetching user details with an invalid ID and assert failure
    }

    @Test
    void shouldUpdateUserDetailsSuccessfully() {
        // TODO: Implement test: Update user details with valid data and assert success
    }

    @Test
    void shouldFailToUpdateUserDetailsWithInvalidData() {
        // TODO: Implement test: Attempt updating user details with invalid data and assert failure
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        // TODO: Implement test: Delete a user with a valid user ID and assert success
    }

    @Test
    void shouldFailToDeleteUserWithInvalidId() {
        // TODO: Implement test: Attempt deleting a user with an invalid ID and assert failure
    }


}
