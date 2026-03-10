package com.example.Consistify.Service;

import com.example.Consistify.Entity.Role;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        System.out.println("GOOGLE LOGIN EMAIL: " + email);

        if (email == null) {
            throw new RuntimeException("Email not received from Google OAuth");
        }

        email = email.trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    System.out.println("Creating new user: " + email);

                    User newUser = User.builder()
                            .email(email)
                            .name(name)
                            .role(Role.USER)
                            .password(null)
                            .build();

                    return userRepository.save(newUser);
                });

        return oAuth2User;
    }