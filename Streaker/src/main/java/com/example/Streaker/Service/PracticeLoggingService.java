package com.example.Streaker.Service;

import com.example.Streaker.DTO.PracticeLogRequest;
import com.example.Streaker.DTO.PracticeResponseDTO;
import com.example.Streaker.Entity.PracticeSession;
import com.example.Streaker.Entity.Skill;
import com.example.Streaker.Mapper.PracticeMapper;
import com.example.Streaker.Repo.PracticeSessionRepository;
import com.example.Streaker.Repo.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PracticeLoggingService {

    private final PracticeSessionRepository practiceSessionRepository;
    private final SkillRepository skillRepository;
    private final PracticeMapper practiceMapper;

    // Constructor Injection of Repository
    public PracticeLoggingService(PracticeSessionRepository practiceSessionRepository,
            SkillRepository skillRepository, PracticeMapper practiceMapper) {
        this.practiceSessionRepository = practiceSessionRepository;
        this.skillRepository = skillRepository;
        this.practiceMapper = practiceMapper;
    }

    //Logs a practice session for a given skill.//
    public void logPractice(PracticeLogRequest request) {

        //  Fetch active skill (practice must belong to an active skill)
        Skill skill = skillRepository
                .findByIdAndActiveTrue(request.getSkillId()) // Here the Repo checks the skill with database
                .orElseThrow(() -> new IllegalArgumentException("Skill not found or inactive")); // if the skill not found the give this statement

        //  Validate duration (must be meaningful) like it should be more than 0
        if (request.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero"); // if not it gives this message
        }
        PracticeSession session = practiceMapper.toEntity(request);
        session.setSkill(skill);
        practiceSessionRepository.save(session);
    }
    public List<PracticeResponseDTO> getAllSessions() {
        return practiceSessionRepository.findAll().stream()
                .map(practiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
