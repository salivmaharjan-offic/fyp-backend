package com.hardware.entities.records;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String message,
        HttpStatus status
) {
}
