package com.example.Streaker.Config;

import com.example.Streaker.Service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // Automatically injects required dependencies
public class SecurityConfig {

    // Custom OAuth2 service to map Google user to application user
    private final CustomOAuth2UserService customOAuth2UserService;

    // Bean to encrypt and verify user passwords using BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Main Spring Security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for easier API and GraphQL usage
                .csrf(csrf -> csrf.disable())

                // Configure public and secured endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/login/**",
                                "/graphiql/**",      // GraphiQL UI
                                "/graphql/**",       // GraphQL API
                                "/_graphiql/**",     // Internal GraphiQL assets
                                "/vendor/**",
                                "/favicon.ico",
                                "/oauth2/**"
                        ).permitAll() // Allow access without authentication
                        .anyRequest().authenticated() // Secure all other endpoints
                )

                // Ignore CSRF protection for GraphQL endpoint
                .csrf(csrf -> csrf.ignoringRequestMatchers("/graphql/**"))

                // Enable form-based login
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                )

                // Enable OAuth2 login (Google, GitHub, etc.)
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // Load and save OAuth2 user
                        )
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                )

                // Configure logout behavior
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Redirect after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                // Session management configuration
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        // Build and return the security filter chain
        return http.build();
    }
}
