package com.hardware.dtos.Auth;

import com.hardware.dtos.UserDTO;
import lombok.*;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String token;
    private UserDTO userDTO;
}
