package org.castle.djames.bankease.user.dto;

import jakarta.validation.constraints.NotBlank;
import org.castle.djames.bankease.user.entity.Role;

public record RegisterUserRequest(@NotBlank String username,
                                  @NotBlank String password,
                                  @NotBlank String email,
                                  @NotBlank String firstName,
                                  @NotBlank String lastName,
                                  @NotBlank Role role) {
}
