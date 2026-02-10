package com.example.Consistify.Service;

import com.example.Consistify.DTO.UserProgressDTO;
import com.example.Consistify.Entity.PracticeSession;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Repo.PracticeSessionRepository;
import com.example.Consistify.Repo.SkillRepository;
import com.example.Consistify.Repo.UserRepository;
import com.example.Consistify.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class UserProgressService {

    private final SkillRepository skillRepository;
    private final PracticeSessionRepository practiceRepository;
    private final UserRepository userRepository;

    public UserProgressService(
            SkillRepository skillRepository,
            PracticeSessionRepository practiceRepository,
            UserRepository userRepository
    ) {
        this.skillRepository = skillRepository;
        this.practiceRepository = practiceRepository;
        this.userRepository = userRepository;
    }

    public UserProgressDTO getMyProgress() {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int totalSkills = skillRepository.countByUser(user);

        int totalMinutes = practiceRepository.findAllByUser(user)
                .stream()
                .mapToInt(PracticeSession::getDurationMinutes)
                .sum();



        LocalDate lastPracticeDate = practiceRepository
                .findTopBySkill_UserOrderByPracticeDateDesc(user)
                .map(PracticeSession::getPracticeDate)
                .orElse(null);

        UserProgressDTO dto = new UserProgressDTO();
        dto.setTotalSkills(totalSkills);

        // TEMP — will be computed properly in next step
        dto.setActiveSkills(0);
        dto.setAtRiskSkills(0);

        dto.setTotalPracticeMinutes(totalMinutes);
        dto.setLastPracticeDate(lastPracticeDate);

        return dto;
    }
}
