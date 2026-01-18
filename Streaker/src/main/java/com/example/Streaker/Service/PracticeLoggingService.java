package com.example.Streaker.Service;

import com.example.Streaker.DTO.PracticeLogRequest;
import com.example.Streaker.DTO.PracticeResponseDTO;
import com.example.Streaker.Entity.PracticeSession;
import com.example.Streaker.Entity.Skill;
import com.example.Streaker.Entity.User;
import com.example.Streaker.Mapper.PracticeMapper;
import com.example.Streaker.Repo.PracticeSessionRepository;
import com.example.Streaker.Repo.SkillRepository;
import com.example.Streaker.Repo.UserRepository;
import com.example.Streaker.util.SecurityUtil;
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
    public void logPractice(PracticeLogRequest request) {

        // Get the email of the currently logged-in user
        String email = SecurityUtil.getCurrentUserEmail();

        // Fetch the user from the database using email
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in database"));

        // Fetch the skill and ensure it belongs to the logged-in user and is active
        Skill skill = skillRepository
                .findByIdAndUserAndActiveTrue(request.getSkillId(), currentUser)
                .orElseThrow(() ->
                        new IllegalArgumentException("Skill not found or belongs to another user")
                );

        // Validate that practice duration is valid
        if (request.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }

        // Convert request DTO to PracticeSession entity
        PracticeSession session = practiceMapper.toEntity(request);

        // Link session with the correct skill and user
        session.setSkill(skill);
        session.setUser(currentUser);

        // Save the practice session to the database
        practiceSessionRepository.save(session);
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
                .findByUser(currentUser)
                .stream()
                .map(practiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
