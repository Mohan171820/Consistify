package com.example.Consistify.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SkillCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    private Integer decayDays;

}
