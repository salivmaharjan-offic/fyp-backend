package com.hardware.entities;

import com.hardware.entities.enums.AuthProvider;
import com.hardware.entities.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String otp;

    private LocalDateTime expiryTime;

    @Builder.Default
    private Integer attempts = 0;

    private LocalDateTime resendAvailableAt;
}
