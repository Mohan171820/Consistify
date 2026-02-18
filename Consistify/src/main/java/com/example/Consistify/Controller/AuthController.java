package com.example.Consistify.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Value("${app.frontend.url:https://consistify-six.vercel.app}")
    private String frontendUrl;

    @GetMapping("/api/auth/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof OAuth2User oauthUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("name", oauthUser.getAttribute("name"));
        response.put("email", oauthUser.getAttribute("email"));

        return ResponseEntity.ok(response);
    }
    @GetMapping("/oauth-success")
    public void oauthSuccess(HttpServletResponse response) throws IOException {
        response.sendRedirect(frontendUrl + "/login.html?auth=success");
    }

}
