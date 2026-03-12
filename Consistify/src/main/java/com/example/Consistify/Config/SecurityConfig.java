package com.example.Consistify.Config;

import com.example.Consistify.Service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                // No CORS bean needed — frontend is served by Spring on the same origin

                .authorizeHttpRequests(auth -> auth
                        // Static files — anyone can load the HTML pages
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/login.html",
                                "/oauth2/**",
                                "/login/**",
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/me",
                                "/error"
                        ).permitAll()

                        // Swagger requires login
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").authenticated()

                        // Everything else requires a session
                        .anyRequest().authenticated()
                )

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
                        // After Google login → go straight to the app
                        .defaultSuccessUrl("/index.html", true)
                        .failureUrl("/login.html?error=true")
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login.html")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }
    // Add this bean inside SecurityConfig
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}