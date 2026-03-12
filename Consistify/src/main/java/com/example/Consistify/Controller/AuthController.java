package com.example.Consistify.Controller;

import com.example.Consistify.DTO.AuthRequest;
import com.example.Consistify.DTO.AuthResponse;
import com.example.Consistify.Entity.Role;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class AuthController {

    @Value("${app.frontend.url:http://localhost:550}")
    private String frontendUrl;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    // ── GET CURRENT USER ──
    @GetMapping("/api/auth/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Object principal = authentication.getPrincipal();
        Map<String, Object> response = new HashMap<>();

        if (principal instanceof OAuth2User oauthUser) {
            response.put("name",  oauthUser.getAttribute("name"));
            response.put("email", oauthUser.getAttribute("email"));
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails ud) {
            response.put("name",  ud.getUsername());
            response.put("email", ud.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(response);
    }

    // ── OAUTH SUCCESS REDIRECT ──
    @GetMapping("/oauth-success")
    public void oauthSuccess(HttpServletResponse response) throws IOException {
        response.sendRedirect(frontendUrl + "/index.html");
    }

    // ── REGISTER ──
    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req, HttpServletRequest request) {
        String email = req.getEmail().trim().toLowerCase();
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        if (req.getPassword() == null || req.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters");
        }
        User user = User.builder()
                .email(email)
                .name(req.getName())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // Auto-login after register
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return ResponseEntity.ok(new AuthResponse(user.getName(), user.getEmail()));
    }

    // ── LOGIN ──
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req, HttpServletRequest request) {
        try {
            String email = req.getEmail().trim().toLowerCase();
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, req.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            User user = userRepository.findByEmail(email).orElseThrow();
            return ResponseEntity.ok(new AuthResponse(user.getName(), user.getEmail()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}