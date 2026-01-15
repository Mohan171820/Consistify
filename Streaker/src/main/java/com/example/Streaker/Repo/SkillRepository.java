package com.example.Streaker.Repo;

import com.example.Streaker.Entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    // this Optional is to get skills of a user by using ID
    Optional<Skill> findByIdAndActiveTrue(Long id);

    //  for security (we will use this next)
    Optional<Skill> findByIdAndUserIdAndActiveTrue(Long id, Long userId);
}
