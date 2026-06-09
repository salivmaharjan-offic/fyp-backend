package com.hardware.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ResendDTO {
    @Email
    private String email;
}
