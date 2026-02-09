package com.example.Consistify.DTO;

import com.example.Consistify.Entity.EffortLevel;
import lombok.Builder;
import java.time.LocalDate;

// This DTO is used to send practice session data back to the client
@Builder
public record PracticeResponseDTO(

        // ID of the practiced skill
        Long skillId,

        // Name of the practiced skill
        String skillName,

        // Date on which the practice was done
        LocalDate practiceDate,

        // Duration of the practice session in minutes
        int durationMinutes,

        // Effort level applied during the practice
        EffortLevel effortLevel
) {
}
