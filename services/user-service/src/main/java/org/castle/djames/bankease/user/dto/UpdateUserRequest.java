package org.castle.djames.bankease.user.dto;

import org.castle.djames.bankease.user.entity.Role;

public record UpdateUserRequest(String password,
                                String email,
                                String firstName,
                                String lastName,
                                Role role,
                                Boolean active) {
}