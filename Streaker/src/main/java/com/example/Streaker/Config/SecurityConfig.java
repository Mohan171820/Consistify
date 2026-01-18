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
@RequiredArgsConstructor // This automatically injects the CustomOAuth2UserService
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disabled for simplified API interaction
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/login/**",
                                "/graphiql/**",      // The UI page
                                "/graphql/**",       // The Data API
                                "/_graphiql/**",     // INTERNAL ASSETS (Fixes the 302 errors)
                                "/vendor/**",
                                "/favicon.ico",
                                "/oauth2/**"
                        ).permitAll() // Publicly accessible endpoints
                        .anyRequest().authenticated() // Everything else requires a login
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/graphql/**"))
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // Maps Google User to DB User
                        )
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Redirect home after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }
}