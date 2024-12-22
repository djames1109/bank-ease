package org.castle.djames.bankease.user.dto;

import lombok.Builder;
import lombok.Data;
import org.castle.djames.bankease.user.entity.Role;

@Data
@Builder
public class UserResponse {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean isActive;

}
