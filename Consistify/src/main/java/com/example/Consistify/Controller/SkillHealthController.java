package com.example.Consistify.Controller;

import com.example.Consistify.DTO.SkillHealthDTO;
import com.example.Consistify.Service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// Base URL for skill health endpoints
@RequestMapping("/api/v1/skills/health")
@RequiredArgsConstructor
public class SkillHealthController {

 // Injecting SkillService to handle business logic related to skill health
    private final SkillService skillService;

    // Endpoint to retrieve skill health information for the current user
    @GetMapping
    public ResponseEntity<List<SkillHealthDTO>> getSkillHealth() {
        return ResponseEntity.ok(skillService.getSkillHealthForCurrentUser());
    }
}
