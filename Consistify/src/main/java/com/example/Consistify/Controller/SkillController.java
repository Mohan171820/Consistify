package com.example.Consistify.Controller;

import com.example.Consistify.DTO.SkillCreateRequest;
import com.example.Consistify.DTO.SkillResponseDTO;
import com.example.Consistify.Service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    // POST /api/skills
    @PostMapping
    public ResponseEntity<String> addSkill(
            @Valid @RequestBody SkillCreateRequest request) {

        skillService.createSkill(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Skill added successfully");
    }

    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getMySkills(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(
                skillService.getSkillsForUser(userId)
        );
    }

}
