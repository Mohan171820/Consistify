package com.example.Streaker.Repo;

import com.example.Streaker.Entity.Skill;
import com.example.Streaker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByIdAndUserAndActiveTrue(Long id, User user);

    Optional<Skill> findByIdAndUserIdAndActiveTrue(Long id, Long userId);
}