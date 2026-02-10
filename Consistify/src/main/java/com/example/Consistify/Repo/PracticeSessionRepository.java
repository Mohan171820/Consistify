package com.example.Consistify.Repo;

import com.example.Consistify.Entity.PracticeSession;
import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

    List<PracticeSession> findBySkill_User(User user);

    List<PracticeSession> findBySkillAndSkill_User(Skill skill, User user);

    List<PracticeSession> findBySkill_NameIgnoreCaseAndSkill_User(String skillName, User user);

    List<PracticeSession> findBySkill_User_Id(Long userId);

    boolean existsBySkillAndPracticeDate(Skill skill, LocalDate practiceDate);

    Optional<PracticeSession>
    findTopBySkillAndSkill_UserOrderByPracticeDateDesc(Skill skill, User user);

    Optional<PracticeSession>
    findTopBySkill_UserOrderByPracticeDateDesc(User user);

    List<PracticeSession>
    findBySkill_UserAndPracticeDateBetween(User user, LocalDate start, LocalDate end);
}
