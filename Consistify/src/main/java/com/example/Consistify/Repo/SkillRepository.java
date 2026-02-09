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

    // Find active skill by ID and User entity
    Optional<Skill> findByIdAndUserAndActiveTrue(Long id, User user);

    // Find active skill by ID and User ID
    Optional<Skill> findByIdAndUserIdAndActiveTrue(Long id, Long userId);

    // Check if active skill already exists for user (to prevent duplicates)
    boolean existsByUserIdAndNameIgnoreCaseAndActiveTrue(Long userId, String name);

    // Fetch all skills for a user
    List<Skill> findByUser(User user);

    // Fetch only active skills for a user
    List<Skill> findByUserAndActiveTrue(User user);

    // Analytics / Progress
    int countByUser(User user);

    int countByUserAndActiveTrue(User user);

    // For decay logic (skills not practiced recently)
    List<Skill> findByUserAndActiveTrueAndLastPracticedDateBefore(User user, LocalDate date);
}
