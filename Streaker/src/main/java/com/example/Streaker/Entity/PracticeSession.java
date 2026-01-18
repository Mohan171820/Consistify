package com.example.Streaker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
// Prevents logging the same skill twice on the same date
@Table(name = "practice_sessions", uniqueConstraints = @UniqueConstraint(columnNames = {"skill_id", "practice_date"})
)
@Data // Lombok generates getters, setters, toString, equals, and hashCode
@AllArgsConstructor
@NoArgsConstructor
public class PracticeSession {

    // Primary key for the practice session
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Links the practice session to a skill using skill_id column
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    // Date on which the practice was done
    @Column(nullable = false)
    private LocalDate practiceDate;

    // Duration of practice in minutes (used to determine effort level)
    @Column(nullable = false)
    private int durationMinutes;

    // Stores effort level as a string to avoid invalid enum values
    @Enumerated(EnumType.STRING)
    private EffortLevel effortLevel;

    // Links the practice session to a specific user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Optional notes about the practice session
    private String notes;
}
