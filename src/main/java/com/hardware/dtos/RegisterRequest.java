package com.hardware.dtos;

import com.hardware.dtos.enums.AuthProvider;
import com.hardware.dtos.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Role role;

    private AuthProvider authProvider;
}
