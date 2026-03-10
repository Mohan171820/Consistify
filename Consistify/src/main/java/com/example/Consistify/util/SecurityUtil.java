package com.example.Consistify.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static String getCurrentUserEmail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("No user is currently logged in. Please log in via Google/Browser first.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            return email.trim().toLowerCase();   // 🔹 normalize
        }

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername().trim().toLowerCase();
        }

        if (principal instanceof String s) {
            return s.trim().toLowerCase();
        }

        throw new RuntimeException("Unsupported principal type: " + principal.getClass().getName());
    }
}