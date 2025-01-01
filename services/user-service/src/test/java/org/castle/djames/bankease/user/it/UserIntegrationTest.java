package org.castle.djames.bankease.user.it;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @Container
    @ServiceConnection(name = "postgres")
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine3.19")
            .withStartupTimeout(Duration.ofSeconds(90))
            .withInitScript("init.sql");

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
    void shouldCreateUserSuccessfully() {
        // TODO: Implement test: Create a user with valid data and assert success
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
