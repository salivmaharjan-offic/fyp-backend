package com.hardware.services;

import com.hardware.dtos.*;
import com.hardware.dtos.enums.AuthProvider;
import com.hardware.entities.PendingRegistration;
import com.hardware.entities.User;
import com.hardware.exceptions.BadRequestException;
import com.hardware.exceptions.ResourceNotFoundException;
import com.hardware.mapper.UserMapper;
import com.hardware.repositories.PendingRegistrationRepository;
import com.hardware.repositories.UserRepository;
import com.hardware.security.Jwt.JwtService;
import com.hardware.security.OPT.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PendingRegistrationRepository pendingRepository;
    private final OtpService otpService;
    private final EmailService emailService;



    // Registration
    public String register(RegisterRequest request) throws BadRequestException {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException(
                    "Email already exists."
            );
        }

        pendingRepository.deleteByEmail(request.getEmail());

        String otp = otpService.generateOtp();

        PendingRegistration pending =
                PendingRegistration.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .authProvider(AuthProvider.LOCAL)
                        .otp(otp)
                        .expiryTime(LocalDateTime.now().plusMinutes(5))
                        .attempts(0)
                        .resendAvailableAt(
                                LocalDateTime.now()
                                        .plusSeconds(60)
                        )
                        .build();

        pendingRepository.save(pending);

        emailService.sendOtp(
                request.getEmail(),
                otp
        );

        return "OTP sent successfully.";
    }

    public AuthResponse verify(VerifyDTO request) throws BadRequestException {

        PendingRegistration pending = pendingRepository.findByEmail(request.getEmail())
                        .orElseThrow(() -> new BadRequestException("OTP not found."));

        if (pending.getExpiryTime()
                .isBefore(
                        LocalDateTime.now()
                )) {
            throw new BadRequestException(
                    "OTP expired."
            );
        }

        if (pending.getAttempts() >= 5) {

            pendingRepository.delete(
                    pending
            );

            throw new BadRequestException(
                    "Maximum verification attempts exceeded. Please register again."
            );
        }

        if (!pending.getOtp()
                .equals(request.getOtp())) {

            pending.setAttempts(
                    pending.getAttempts() + 1
            );

            pendingRepository.save(
                    pending
            );

            throw new BadRequestException(
                    "Invalid OTP. Remaining attempts: "
                            + (5 - pending.getAttempts())
            );
        }

        User user =
                User.builder()
                        .username(pending.getUsername())
                        .email(pending.getEmail())
                        .password(pending.getPassword())
                        .role(pending.getRole())
                        .authProvider(AuthProvider.LOCAL)
                        .build();

        repository.save(user);

        pendingRepository.delete(pending);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .userDTO(UserMapper.toDTO(user))
                .build();
    }

    public String resendOtp(
            ResendDTO request
    ) throws BadRequestException {

        PendingRegistration pending =
                pendingRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new BadRequestException(
                                        "Registration not found."
                                )
                        );

        if (pending.getResendAvailableAt()
                .isAfter(LocalDateTime.now())) {

            long secondsRemaining =
                    Duration.between(
                            LocalDateTime.now(),
                            pending.getResendAvailableAt()
                    ).getSeconds();

            throw new BadRequestException(
                    "Please wait "
                            + secondsRemaining
                            + " seconds before requesting another OTP."
            );
        }

        String otp =
                otpService.generateOtp();

        pending.setOtp(otp);

        pending.setAttempts(0);

        pending.setExpiryTime(
                LocalDateTime.now()
                        .plusMinutes(5)
        );

        pending.setResendAvailableAt(
                LocalDateTime.now()
                        .plusSeconds(60)
        );

        pendingRepository.save(pending);

        emailService.sendOtp(
                pending.getEmail(),
                otp
        );

        return "OTP resent successfully.";
    }

    public AuthResponse login(LoginRequest  request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User with given email not found:" + request.getEmail()));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .userDTO(UserMapper.toDTO(user))
                .build();
    }
}
