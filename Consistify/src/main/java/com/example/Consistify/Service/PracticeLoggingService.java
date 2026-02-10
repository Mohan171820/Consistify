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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PracticeLoggingService {

    // Repository to store and fetch practice sessions
    private final PracticeSessionRepository practiceSessionRepository;

    // Repository to validate and fetch skills
    private final SkillRepository skillRepository;

    // Mapper to convert between DTOs and entities
    private final PracticeMapper practiceMapper;

    // Repository to fetch logged-in user details
    private final UserRepository userRepository;

    // Constructor to inject required dependencies
    public PracticeLoggingService(PracticeSessionRepository practiceSessionRepository,
                                  SkillRepository skillRepository,
                                  PracticeMapper practiceMapper,
                                  UserRepository userRepository) {
        this.practiceSessionRepository = practiceSessionRepository;
        this.skillRepository = skillRepository;
        this.practiceMapper = practiceMapper;
        this.userRepository = userRepository;
    }

    // Logs a new practice session for the currently logged-in user
    @Transactional
    public void logPractice(PracticeLogRequest request) {

        String email = SecurityUtil.getCurrentUserEmail();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in database"));

        Skill skill = skillRepository
                .findByIdAndUser(request.getSkillId(), currentUser)
                .orElseThrow(() ->
                        new IllegalArgumentException("Skill not found or does not belong to current user")
                );

        if (request.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }

        PracticeSession session = practiceMapper.toEntity(request);
        session.setSkill(skill);

        skill.setLastPracticedDate(request.getPracticeDate());

        // Save both
        practiceSessionRepository.save(session);
        skillRepository.save(skill);
    }

    // Retrieves all practice sessions for the currently logged-in user
    public List<PracticeResponseDTO> getAllSessions() {

        // Get email of the logged-in user
        String email = SecurityUtil.getCurrentUserEmail();

        // If no user is logged in, return an empty list
        if (email == null) {
            return List.of();
        }

        // Fetch user details from the database
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch user's practice sessions and convert them to response DTOs
        return practiceSessionRepository
                .findBySkill_User(currentUser)
                .stream()
                .map(practiceMapper::toResponseDTO)
                .collect(Collectors.toList());

    }
}
