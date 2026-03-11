package com.example.Consistify.Service;

import com.example.Consistify.DTO.PracticeLogRequest;
import com.example.Consistify.DTO.PracticeResponseDTO;
import com.example.Consistify.Entity.PracticeSession;
import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.User;
import com.example.Consistify.Mapper.PracticeMapper;
import com.example.Consistify.Repo.PracticeSessionRepository;
import com.example.Consistify.Repo.SkillRepository;
import com.example.Consistify.Repo.UserRepository;
import com.example.Consistify.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PracticeLoggingService {

    private final PracticeSessionRepository practiceRepository;
    private final SkillRepository skillRepository;
    private final PracticeMapper practiceMapper;
    private final UserRepository userRepository;

    // ---------------- LOG PRACTICE SESSION ----------------
    // Method to log a new practice session for the current user
    @Transactional
    public void logPractice(PracticeLogRequest request) {

        // Get current users email from security context and fetch user entity
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the skill being practiced and ensure it belongs to the user
        Skill skill = skillRepository
                .findByIdAndUser(request.getSkillId(), user)
                .orElseThrow(() ->
                        new IllegalArgumentException("Skill not found or inactive")
                );

        // Validate that the practice duration is greater than zero
        if (request.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }
        boolean exists = practiceRepository
                .existsBySkillAndPracticeDate(skill, request.getPracticeDate());

        if (exists) {
            throw new IllegalStateException("You already logged this skill today.");
        }

        // Create a new practice session entity from the request DTO, set its skill and user, and save it
        PracticeSession session = practiceMapper.toEntity(request);
        session.setSkill(skill);
        session.setUser(user);

        skill.setLastPracticedDate(request.getPracticeDate());

        practiceRepository.save(session);
        skillRepository.save(skill);
    }

    // ---------------- GET MY PRACTICES (PAGINATED) ----------------
    @Transactional
    public Page<PracticeResponseDTO> getMyPracticeSessions(Pageable pageable) {
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return practiceRepository
                .findByUser(user, pageable)
                .map(practiceMapper::toResponseDTO);
    }
    // ---------------- GET MY PRACTICES FOR GRAPHQL (ALL) ----------------
    @Transactional
    public List<PracticeResponseDTO> getMyPracticeSessionsForGraphQL() {
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return practiceRepository
                .findTopBySkill_UserOrderByPracticeDateDesc(user)
                .stream()
                .map(practiceMapper::toResponseDTO)
                .toList();
    }

}
