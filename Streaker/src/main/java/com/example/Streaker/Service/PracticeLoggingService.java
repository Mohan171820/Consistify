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

    private final PracticeSessionRepository practiceSessionRepository;
    private final SkillRepository skillRepository;
    private final PracticeMapper practiceMapper;
    private final UserRepository userRepository;

    // Constructor to inject all necessary repositories and the mapper
    public PracticeLoggingService(PracticeSessionRepository practiceSessionRepository,
                                  SkillRepository skillRepository,
                                  PracticeMapper practiceMapper,
                                  UserRepository userRepository) {
        this.practiceSessionRepository = practiceSessionRepository;
        this.skillRepository = skillRepository;
        this.practiceMapper = practiceMapper;
        this.userRepository = userRepository;
    }

    /**
     * This method logs a new practice session and links it to the logged-in user.
     */
    public void logPractice(PracticeLogRequest request) {
        // First, we get the email of the person currently logged in via Google
        String email = SecurityUtil.getCurrentUserEmail();

        // We find that specific User in our database using their email
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in database"));

        // Fetch the skill from the database and make sure it is actually active
        Skill skill = skillRepository
                .findByIdAndUserAndActiveTrue(request.getSkillId(), currentUser) // The new name
                .orElseThrow(() -> new IllegalArgumentException("Skill not found or belongs to another user"));
        // Basic validation to ensure the practice time is at least 1 minute
        if (request.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }

        // Convert the Request Data (DTO) into a Database Object (Entity)
        PracticeSession session = practiceMapper.toEntity(request);

        // Link the session to the correct Skill and the logged-in User
        session.setSkill(skill);
        session.setUser(currentUser);

        // Save the session details into the database
        practiceSessionRepository.save(session);
    }

    /**
     * This method retrieves all practice sessions belonging ONLY to the logged-in user.
     */
    public List<PracticeResponseDTO> getAllSessions() {
        // Identify who is asking for the data by getting their email
        String email = SecurityUtil.getCurrentUserEmail();

        // Find the user record in our database
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch only the sessions for this user and convert them to the response format (DTO)
        return practiceSessionRepository
                .findByUser(currentUser)
                .stream()
                .map(practiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}