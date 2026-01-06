package com.example.Streaker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
// Constrains to avoid log same skill twice
@Table(name = "practice_sessions",uniqueConstraints = @UniqueConstraint(columnNames = {"skill_id", "practice_date"})
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PracticeSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "skill_id")
    private Skill skill;
    @Column(nullable = false)
    private LocalDate practiceDate;
    @Column(nullable = false)
    private int durationMinutes;
// Enum allows only existed values and avoids invalid data so it prevents the false info in practice_sessions
    @Enumerated(EnumType.STRING)
    private EffortLevel effortLevel;

    private String notes;

}
