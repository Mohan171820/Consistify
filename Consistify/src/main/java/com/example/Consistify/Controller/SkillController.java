package com.example.Consistify.Controller;

import com.example.Consistify.DTO.SkillCreateRequest;
import com.example.Consistify.DTO.SkillResponseDTO;
import com.example.Consistify.Service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
// Base URL for all skill-related endpoints
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    // POST /api/v1/skills
    @PostMapping
    public ResponseEntity<Map<String, String>> addSkill(
            @Valid @RequestBody SkillCreateRequest request
    ) {
        skillService.createSkill(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Skill added successfully"));
    }

    // GET /api/v1/skills
    @GetMapping
    public ResponseEntity<Page<SkillResponseDTO>> getMySkills(
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                skillService.getSkillsForCurrentUser(pageable)
        );
    }
}

