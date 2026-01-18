package com.example.Streaker.DTO;

import com.example.Streaker.Entity.EffortLevel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// This DTO is used to receive practice log data from the client
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeLogRequest {

    // Ensures the skill ID is provided
    @NotNull(message = "Skill ID is required")
    private Long skillId;

    // Makes sure the practice date is provided and not in the future
    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate practiceDate;

    // Ensures the practice duration is at least 1 minute
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationMinutes;

    // Forces the user to select the effort level for the practice
    @NotNull(message = "Effort level is required")
    private EffortLevel effortLevel;

    // Optional field for additional notes about the practice
    private String notes;
}
