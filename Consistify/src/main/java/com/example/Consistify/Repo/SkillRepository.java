package com.example.Consistify.Repo;

import com.example.Consistify.Entity.Skill;
import com.example.Consistify.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    // Ownership-safe fetch
    Optional<Skill> findByIdAndUser(Long id, User user);

    // Fetch all skills of a user
    List<Skill> findByUser(User user);

    // Prevent duplicate skill names per user
    boolean existsByUserAndNameIgnoreCase(User user, String name);

    // Simple counts (raw data only)
    int countByUser(User user);

    // For decay evaluation (raw data)
    List<Skill> findByUserAndLastPracticedDateBefore(User user, LocalDate date);
}
