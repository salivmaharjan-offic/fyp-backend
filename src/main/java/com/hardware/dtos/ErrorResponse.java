package com.hardware.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private String message;
}
