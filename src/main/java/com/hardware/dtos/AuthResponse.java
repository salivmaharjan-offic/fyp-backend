package com.hardware.dtos;

import lombok.*;

@Getter
@Setter
@Builder
public class AuthResponse {
    private String token;
    private UserDTO userDTO;
}
