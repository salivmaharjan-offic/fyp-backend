package com.hardware.security;

import com.hardware.entities.User;
import com.hardware.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        OAuth2User oauthUser =
                (OAuth2User)
                        authentication.getPrincipal();

        String email =
                oauthUser.getAttribute("email");

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        String token =
                jwtService.generateToken(user);

        response.sendRedirect(
                "http://localhost:5173" +
                        "/oauth-success?token=" +
                        token
        );
    }
}
