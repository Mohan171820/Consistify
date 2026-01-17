package com.example.Streaker.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        //  Handle Google/OAuth2 Principal
        if (principal instanceof OAuth2User oauth2User) {
            return oauth2User.getAttribute("email");
        }

        //  Handle standard Form Login Principal
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        throw new RuntimeException("Unsupported principal type: " + principal.getClass().getName());
    }
}