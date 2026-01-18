package com.example.Streaker.Repo;

import com.example.Streaker.Entity.PracticeSession;
import com.example.Streaker.Entity.Skill;
import com.example.Streaker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

    // Finds all practice sessions linked to a specific user
    List<PracticeSession> findByUser(User user);

    // Finds all practice sessions using the user ID through the Skill entity
    List<PracticeSession> findBySkillUserId(Long userId);

    // Checks if a practice session already exists for a skill on a given date
    boolean existsBySkillAndPracticeDate(Skill skill, LocalDate practiceDate);
}
