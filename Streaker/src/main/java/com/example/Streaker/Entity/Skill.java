package com.example.Streaker.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="skills")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDecayDays() {
        return decayDays;
    }

    public void setDecayDays(int decayDays) {
        this.decayDays = decayDays;
    }
}
