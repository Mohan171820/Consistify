package com.example.Consistify.Controller;

import com.example.Consistify.DTO.UserProgressDTO;
import com.example.Consistify.Service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Base URL for all endpoints in this controller
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class UserProgressController {
    private final UserProgressService userProgressService;

    // Endpoint to get the current user's progress in their fitness journey
    @GetMapping("/me")
    public UserProgressDTO getMyProgress() {
        return userProgressService.getMyProgress();
    }
}
