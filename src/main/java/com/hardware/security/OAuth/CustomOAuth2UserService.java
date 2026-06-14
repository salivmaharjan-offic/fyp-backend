package com.hardware.security.OAuth;

import com.hardware.entities.enums.AuthProvider;
import com.hardware.entities.enums.Role;
import com.hardware.entities.User;
import com.hardware.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService
        extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(
            OAuth2UserRequest request)
            throws OAuth2AuthenticationException {

        OAuth2User oAuthUser =
                super.loadUser(request);

        String email =
                oAuthUser.getAttribute("email");

        String name =
                oAuthUser.getAttribute("name");

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseGet(() -> {

                            User newUser = new User();

                            newUser.setEmail(email);

                            newUser.setUsername(name);

                            newUser.setAuthProvider(
                                    AuthProvider.GOOGLE
                            );

                            newUser.setRole(Role.CUSTOMER);

                            return userRepository.save(
                                    newUser
                            );
                        });

        return oAuthUser;
    }
}