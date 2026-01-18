package com.example.Streaker.util;

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

        // 1. Check if authentication exists and isn't anonymous
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("No user is currently logged in. Please log in via Google/Browser first.");
        }

        Object principal = authentication.getPrincipal();

        // 2. Handle Google/OAuth2 Principal
        if (principal instanceof OAuth2User oauth2User) {
            return oauth2User.getAttribute("email");
        }

        // 3. Handle standard Form Login Principal
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        // 4. Fallback for simple String principals (if any remain)
        if (principal instanceof String s) {
            return s;
        }

        throw new RuntimeException("Unsupported principal type: " + principal.getClass().getName());
    }
}