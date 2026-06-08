package com.hardware.dtos;

import com.hardware.dtos.enums.AuthProvider;
import com.hardware.dtos.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private AuthProvider authProvider;
}
