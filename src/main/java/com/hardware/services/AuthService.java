package com.hardware.services;

import com.hardware.dtos.AuthResponse;
import com.hardware.dtos.LoginRequest;
import com.hardware.dtos.RegisterRequest;
import com.hardware.dtos.UserDTO;
import com.hardware.dtos.enums.AuthProvider;
import com.hardware.dtos.enums.Role;
import com.hardware.entities.User;
import com.hardware.mapper.UserMapper;
import com.hardware.repositories.UserRepository;
import com.hardware.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Registration
    public AuthResponse register(RegisterRequest request) {

        if(request.getRole() == Role.ADMIN){
            throw new RuntimeException("Cannot register as an Administrator!");
        }else{
            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .authProvider(AuthProvider.LOCAL)
                    .build();

            repository.save(user);

            String token = jwtService.generateToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .userDTO(UserMapper.toDTO(user))
                    .build();
        }
    }

    public AuthResponse login(LoginRequest  request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with given email not found:" + request.getEmail()));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .userDTO(UserMapper.toDTO(user))
                .build();
    }
}
