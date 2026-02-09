package com.example.Consistify.DTO;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UserProgressDTO {
    private int totalSkills;
    private int activeSkills;
    private int atRiskSkills;
    private int totalPracticeMinutes;
    private LocalDate lastPracticeDate;
}
