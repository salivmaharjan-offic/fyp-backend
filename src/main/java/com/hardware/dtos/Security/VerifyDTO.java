package com.hardware.dtos.Security;

import lombok.Data;

@Data
public class VerifyDTO {
    private String email;
    private String otp;
}