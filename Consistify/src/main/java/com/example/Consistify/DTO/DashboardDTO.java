package com.example.Consistify.DTO;

import com.example.Consistify.Entity.Skill;

import java.time.LocalDate;

// DTO for dashboard metrics
public class DashboardDTO {
    // Skill metrics
    private int totalSkills;
    private int activeSkills;
    private int atRiskSkills;
    private int decayedSkills;

    private int totalPracticeMinutes;
    private LocalDate lastPracticeDate;
    private int consistencyScore;

    // Getters and setters

    public int getTotalSkills() {
        return totalSkills;
    }

    public void setTotalSkills(int totalSkills) {
        this.totalSkills = totalSkills;
    }

    public int getActiveSkills() {
        return activeSkills;
    }

    public void setActiveSkills(int activeSkills) {
        this.activeSkills = activeSkills;
    }

    public int getAtRiskSkills() {
        return atRiskSkills;
    }

    public void setAtRiskSkills(int atRiskSkills) {
        this.atRiskSkills = atRiskSkills;
    }

    public int getDecayedSkills() {
        return decayedSkills;
    }

    public void setDecayedSkills(int decayedSkills) {
        this.decayedSkills = decayedSkills;
    }

    public int getTotalPracticeMinutes() {
        return totalPracticeMinutes;
    }

    public void setTotalPracticeMinutes(int totalPracticeMinutes) {
        this.totalPracticeMinutes = totalPracticeMinutes;
    }

    public LocalDate getLastPracticeDate() {
        return lastPracticeDate;
    }

    public void setLastPracticeDate(LocalDate lastPracticeDate) {
        this.lastPracticeDate = lastPracticeDate;
    }
}
