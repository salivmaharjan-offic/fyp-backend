package com.hardware.controllers;

import com.hardware.dtos.*;
import com.hardware.entities.User;
import com.hardware.mapper.UserMapper;
import com.hardware.repositories.UserRepository;
import com.hardware.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository  userRepository;

    @PostMapping("/verify")
    public ResponseEntity<AuthResponse>
    verify(
            @RequestBody
            VerifyDTO request
    ) {

        return ResponseEntity.ok(
                authService.verify(request)
        );
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String>
    resendOtp(
            @RequestBody
            ResendDTO request
    ) {

        return ResponseEntity.ok(
                authService.resendOtp(request)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        return ResponseEntity.ok(
                UserMapper.toDTO(user)
        );
    }
}
