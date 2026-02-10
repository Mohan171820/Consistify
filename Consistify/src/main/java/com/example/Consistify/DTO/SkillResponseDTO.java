package com.example.Consistify.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for skill response
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillResponseDTO {

    // Basic skill information
    private Long id;
    private String name;
    private String category;
    private boolean active;
}
