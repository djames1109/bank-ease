package org.castle.djames.bankease.user.ut.service;

import org.castle.djames.bankease.user.dto.RegisterUserRequest;
import org.castle.djames.bankease.user.entity.Role;
import org.castle.djames.bankease.user.entity.User;
import org.castle.djames.bankease.user.repository.UserRepository;
import org.castle.djames.bankease.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void registerUser() {
        // Arrange
        when(userRepository.save(Mockito.any())).thenReturn(mockUser());
        var request = new RegisterUserRequest("username", "password", "email@domain.com", "firstName", "lastName", Role.USER);

        // Act
        var response = userService.registerUser(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo(request.username());
        assertThat(response.getEmail()).isEqualTo(request.email());
        assertThat(response.getFirstName()).isEqualTo(request.firstName());
        assertThat(response.getLastName()).isEqualTo(request.lastName());
        assertThat(response.getRole()).isEqualTo(request.role());

        verify(userRepository).save(Mockito.any());
    }

    @Test
    void getUserByUsername() {
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(mockUser()));

        userService.getUserByUsername("username");

        verify(userRepository).findByUsername(Mockito.anyString());

    }

    @Test
    void search() {
        var pageRequest = PageRequest.of(0, 1);
        var page = new PageImpl<>(List.of(mockUser()), pageRequest, 1);
        when(userRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(page);

        // Act
        var results = userService.search("username:johndoe", pageRequest);

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getUsername()).isEqualTo("username");
        verify(userRepository).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    private User mockUser() {
        return User.builder()
                .username("username")
                .password(passwordEncoder.encode("password"))
                .email("email@domain.com")
                .firstName("firstName")
                .lastName("lastName")
                .role(Role.USER)
                .active(true)
                .build();
    }
}