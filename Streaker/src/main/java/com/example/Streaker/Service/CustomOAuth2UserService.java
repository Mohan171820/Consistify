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

    // Repository used to fetch and store users
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        // 1. Fetch user details from OAuth provider (Google)
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. Extract required user information
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // 3. Check if the user already exists in the database
        Optional<User> userOptional = userRepository.findByEmail(email);

        // 4. If user does not exist, create and save a new user
        if (userOptional.isEmpty()) {
            User newUser = User.builder()
                    .email(email)
                    .name(name)
                    .role(Role.USER)     // Assign default role
                    .password(null)      // OAuth users do not have a local password
                    .build();

            userRepository.save(newUser);
        }

        // 5. Return OAuth user details to Spring Security
        return oAuth2User;
    }
}
