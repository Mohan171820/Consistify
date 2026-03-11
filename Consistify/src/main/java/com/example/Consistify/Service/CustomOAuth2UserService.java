package com.example.Consistify.Service;

import com.example.Consistify.Entity.Role;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.<String>getAttribute("email").trim().toLowerCase();
        String name = oAuth2User.getAttribute("name");

        try {
            userRepository.findByEmail(email)
                    .orElseGet(() -> userRepository.save(
                            User.builder()
                                    .email(email)
                                    .name(name)
                                    .role(Role.USER)
                                    .password(null)
                                    .build()
                    ));
        } catch (Exception e) {
            System.err.println("CRITICAL: Failed to save user " + email + " — " + e.getMessage());
            e.printStackTrace();
        }

        return oAuth2User;
    }
}