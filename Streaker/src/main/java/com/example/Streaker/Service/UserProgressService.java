package com.example.Streaker.Service;

import com.example.Streaker.DTO.UserProgressDTO;
import com.example.Streaker.Entity.PracticeSession;
import com.example.Streaker.Entity.User;
import com.example.Streaker.Repo.PracticeSessionRepository;
import com.example.Streaker.Repo.SkillRepository;
import com.example.Streaker.Repo.UserRepository;
import com.example.Streaker.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class UserProgressService {
    private final SkillRepository skillRepository;
    private final PracticeSessionRepository practiceRepository;
    private final UserRepository userRepository;

    public UserProgressService(SkillRepository skillRepository,
                               PracticeSessionRepository practiceRepository,
                               UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.practiceRepository = practiceRepository;
        this.userRepository = userRepository;
    }

    public UserProgressDTO getMyProgress() {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int totalSkills = skillRepository.countByUser(user);
        int activeSkills = skillRepository.countByUserAndActiveTrue(user);

        int totalMinutes = practiceRepository.findByUser(user)
                .stream()
                .mapToInt(PracticeSession::getDurationMinutes)
                .sum();

        LocalDate lastPracticeDate = practiceRepository
                .findTopByUserOrderByPracticeDateDesc(user)
                .map(PracticeSession::getPracticeDate)
                .orElse(null);

        UserProgressDTO dto = new UserProgressDTO();
        dto.setTotalSkills(totalSkills);
        dto.setActiveSkills(activeSkills);
        dto.setAtRiskSkills(totalSkills - activeSkills); // temp logic
        dto.setTotalPracticeMinutes(totalMinutes);
        dto.setLastPracticeDate(lastPracticeDate);

        return dto;
    }
}
