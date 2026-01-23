package com.example.Streaker.Controller;

import com.example.Streaker.DTO.UserProgressDTO;
import com.example.Streaker.Service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class UserProgressController {
    private final UserProgressService userProgressService;

    @GetMapping("/me")
    public UserProgressDTO getMyProgress() {
        return userProgressService.getMyProgress();
    }
}
