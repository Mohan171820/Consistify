package com.example.Streaker.Repo;

import com.example.Streaker.Entity.PracticeSession;
import com.example.Streaker.Entity.Skill;
import com.example.Streaker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

    // Fetch all practice sessions of a user
    List<PracticeSession> findByUser(User user);

    // Fetch all sessions for a user by skill ID
    List<PracticeSession> findByUserAndSkillId(User user, Long skillId);

    // Fetch all sessions for a user by skill name
    List<PracticeSession> findByUserAndSkillNameIgnoreCase(User user, String skillName);

    // Fetch all sessions via skill owner (alternate method)
    List<PracticeSession> findBySkillUserId(Long userId);

    // Check duplicate practice entry for a skill on same day
    boolean existsBySkillAndPracticeDate(Skill skill, LocalDate practiceDate);

    // Last practice date for a skill (for decay)
    Optional<PracticeSession> findTopByUserAndSkillIdOrderByPracticeDateDesc(User user, Long skillId);

    // Last practice session for user (for progress)
    Optional<PracticeSession> findTopByUserOrderByPracticeDateDesc(User user);

    // Fetch sessions in a date range (analytics / charts)
    List<PracticeSession> findByUserAndPracticeDateBetween(User user, LocalDate start, LocalDate end);
}
