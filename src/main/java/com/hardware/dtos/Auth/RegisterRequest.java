package com.hardware.dtos.Auth;

import com.hardware.entities.enums.AuthProvider;
import com.hardware.entities.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;

    private String email;

    private String password;

    private Role role;

    private AuthProvider authProvider;
}
