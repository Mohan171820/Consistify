package com.example.Consistify.Repo;

import com.example.Consistify.Entity.PracticeSession;
import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

    // Find all practice sessions for a specific user with pagination
    Page<PracticeSession> findByUser(User user, Pageable pageable);

    // Find all practice sessions for a specific skill and user
    List<PracticeSession> findBySkillAndSkill_User(Skill skill, User user);

    // Find all practice sessions for a specific skill name (case-insensitive) and user
    List<PracticeSession> findBySkill_NameIgnoreCaseAndSkill_User(String skillName, User user);

    // Find all practice sessions for a specific user
    List<PracticeSession> findBySkill_User_Id(Long userId);

    // Check if a practice session exists for a specific skill and practice date
    boolean existsBySkillAndPracticeDate(Skill skill, LocalDate practiceDate);

    // Find the most recent practice session for a specific skill and user
    Optional<PracticeSession>
    findTopBySkillAndSkill_UserOrderByPracticeDateDesc(Skill skill, User user);

    // Find the most recent practice session for a specific user
    Optional<PracticeSession>
    findTopBySkill_UserOrderByPracticeDateDesc(User user);

    // Find all practice sessions for a specific user between two dates
    List<PracticeSession>
    findBySkill_UserAndPracticeDateBetween(User user, LocalDate start, LocalDate end);

    // Find all practice sessions for a specific skill ordered by practice date descending
    List<PracticeSession> findBySkillOrderByPracticeDateDesc(Skill skill);

    // Find all practice sessions for a specific user
    List<PracticeSession> findAllByUser(User user);




}
