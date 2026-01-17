package com.example.Streaker.Repo;

import com.example.Streaker.Entity.PracticeSession;
import com.example.Streaker.Entity.Skill;
import com.example.Streaker.Entity.User; // Import the User entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

    // Finds sessions directly linked to the User
    List<PracticeSession> findByUser(User user);

    List<PracticeSession> findBySkillUserId(Long userId);

    boolean existsBySkillAndPracticeDate(Skill skill, LocalDate practiceDate);
}