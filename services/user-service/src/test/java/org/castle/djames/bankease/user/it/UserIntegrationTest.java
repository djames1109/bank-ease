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
    void shouldFailToCreateUserWithInvalidData() {

    }

    @Test
    void shouldFetchUserDetailsSuccessfully() {

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


}
