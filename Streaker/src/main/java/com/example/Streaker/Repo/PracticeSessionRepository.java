package com.example.Streaker.Repo;

import com.example.Streaker.Entity.PracticeSession;
import com.example.Streaker.Entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {
    //this below line is to get user skills by the user id
    List<PracticeSession> findBySkillUserId(Long userId);
    boolean existsBySkillAndPracticeDate(Skill skill, LocalDate practiceDate); // As we had written the condition in service layer it checks with databasae
}
