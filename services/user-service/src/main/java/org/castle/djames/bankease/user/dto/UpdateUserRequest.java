package org.castle.djames.bankease.user.dto;

import jakarta.validation.constraints.Email;
import org.castle.djames.bankease.user.entity.Role;

public record UpdateUserRequest(String password,
                                @Email String email,
                                String firstName,
                                String lastName,
                                Role role,
                                Boolean active) {
}
