package com.example.Streaker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="skills")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Nullable and unique to avoid false and invalid data
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private int decayDays;

}
