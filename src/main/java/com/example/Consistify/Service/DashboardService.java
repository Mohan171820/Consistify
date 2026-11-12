package com.example.Consistify.Service;

import com.example.Consistify.DTO.DashboardDTO;
import com.example.Consistify.DTO.SkillHealthDTO;
import com.example.Consistify.Entity.PracticeSession;
import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Repo.PracticeSessionRepository;
import com.example.Consistify.Repo.SkillRepository;
import com.example.Consistify.Repo.UserRepository;
import com.example.Consistify.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    // Inject repositories and health calculator
    private final SkillRepository skillRepository;
    private final PracticeSessionRepository practiceRepository;
    private final SkillHealthCalculator healthCalculator;
    private final UserRepository userRepository;

    // Method to get dashboard data for the current user
    public DashboardDTO getMyDashboard() {

        // Get current users email from security context and fetch user entity
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch all skills for the user
        List<Skill> skills = skillRepository.findByUser(user);

        // Calculate health status for each skill and count active, at-risk, and decayed skills
        int active = 0, atRisk = 0, decayed = 0;

        // Loop through each skill and calculate its health status
        for (Skill skill : skills) {
            SkillHealthDTO health = healthCalculator.calculate(skill);

            // Increment counters based on health status
            switch (health.getStatus()) {
                case ACTIVE -> active++;
                case AT_RISK -> atRisk++;
                case DECAYED -> decayed++;
            }
        }

        // Calculate total practice minutes across all sessions for the user
        int totalMinutes = practiceRepository.findAllByUser(user)
                .stream()
                .mapToInt(PracticeSession::getDurationMinutes)
                .sum();

        // Get the date of the most recent practice session for the user
        LocalDate lastPracticeDate = practiceRepository
                .findTopBySkill_UserOrderByPracticeDateDesc(user)
                .map(PracticeSession::getPracticeDate)
                .orElse(null);
        int total = skills.size();
        int score = total == 0 ? 0 : (active * 100) / total;

        // Create and return the dashboard DTO with all calculated values
        DashboardDTO dto = new DashboardDTO();
        dto.setTotalSkills(skills.size());
        dto.setActiveSkills(active);
        dto.setAtRiskSkills(atRisk);
        dto.setDecayedSkills(decayed);
        dto.setTotalPracticeMinutes(totalMinutes);
        dto.setLastPracticeDate(lastPracticeDate);

        return dto;
    }
}

