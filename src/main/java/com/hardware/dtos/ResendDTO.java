package com.hardware.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ResendDTO {
    private String email;
}
