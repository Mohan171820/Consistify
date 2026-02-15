package com.example.Consistify.Service;

import com.example.Consistify.DTO.SkillHealthDTO;
import com.example.Consistify.Entity.PracticeSession;
import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.SkillStatus;
import com.example.Consistify.Repo.PracticeSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillHealthCalculator {

    // Inject the practice session repository to access practice data
    private final PracticeSessionRepository practiceRepository;

    // Method to calculate the health status of a skill based on its practice history
    public SkillHealthDTO calculate(Skill skill) {

        // Fetch all practice sessions for the skill, ordered by date descending
        List<PracticeSession> sessions =
                practiceRepository.findBySkillOrderByPracticeDateDesc(skill);

        // Create a DTO to hold the health information for the skill
        SkillHealthDTO dto = new SkillHealthDTO();
        dto.setSkillId(skill.getId());
        dto.setSkillName(skill.getName());

        // If there are no practice sessions, the skill is considered decayed with maximum inactivity
        if (sessions.isEmpty()) {
            dto.setDaysInactive(null);
            dto.setCurrentStreak(0);
            dto.setStatus(SkillStatus.DECAYED);
            return dto;
        }

        // Calculate the number of days since the last practice session
        LocalDate today = LocalDate.now();
        LocalDate lastPracticeDate = sessions.get(0).getPracticeDate();

        // Calculate days inactive using ChronoUnit to find the difference in days between the last practice date and today
        int daysInactive = (int) ChronoUnit.DAYS.between(lastPracticeDate, today);
        dto.setDaysInactive(daysInactive);

       // Calculate the current streak of consecutive practice days
        int streak = calculateStreak(sessions);
        dto.setCurrentStreak(streak);

        dto.setStatus(determineStatus(daysInactive));

        return dto;
    }

    private int calculateStreak(List<PracticeSession> sessions) {

        int streak = 0;

        List<LocalDate> uniqueDates = sessions.stream()
                .map(PracticeSession::getPracticeDate)
                .distinct()
                .toList();

        LocalDate today = LocalDate.now();
        LocalDate previousDate = null;

        for (LocalDate practiceDate : uniqueDates) {

            if (previousDate == null) {
                if (practiceDate.equals(today) || practiceDate.equals(today.minusDays(1))) {
                    streak = 1;
                    previousDate = practiceDate;
                } else {
                    break;
                }
            } else {
                if (practiceDate.equals(previousDate.minusDays(1))) {
                    streak++;
                    previousDate = practiceDate;
                } else {
                    break;
                }
            }
        }

        return streak;
    }


    // Helper method to determine the skill status based on the number of days inactive
    private SkillStatus determineStatus(int daysInactive) {

        if (daysInactive <= 1) {
            return SkillStatus.ACTIVE;
        } else if (daysInactive <= 6) {
            return SkillStatus.AT_RISK;
        } else {
            return SkillStatus.DECAYED;
        }
    }
}