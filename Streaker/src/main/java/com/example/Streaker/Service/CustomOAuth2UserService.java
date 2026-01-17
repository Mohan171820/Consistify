package com.example.Streaker.Service;

import com.example.Streaker.Entity.Role;
import com.example.Streaker.Entity.User;
import com.example.Streaker.Repo.UserRepository;
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
        // 1. Get user info from Google
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. Extract details
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // 3. Save or Update user in our database
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            User newUser = User.builder()
                    .email(email)
                    .name(name) // This will now work!
                    .role(Role.USER) // Set a default role
                    .password(null) // No local password for OAuth users
                    .build();
            userRepository.save(newUser);
        }

        return oAuth2User;
    }
}