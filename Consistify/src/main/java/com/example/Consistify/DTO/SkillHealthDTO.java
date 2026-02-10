package com.example.Consistify.DTO;

import com.example.Consistify.Entity.SkillStatus;

// DTO for skill health status
public class SkillHealthDTO {

    // Basic skill information
    private Long skillId;
    private String skillName;

    private int currentStreak;
    private int daysInactive;
    private SkillStatus status;

    public Long getSkillId() {
        return skillId;
    }

    // Setters and getters
    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getDaysInactive() {
        return daysInactive;
    }

    public void setDaysInactive(int daysInactive) {
        this.daysInactive = daysInactive;
    }

    public SkillStatus getStatus() {
        return status;
    }

    public void setStatus(SkillStatus status) {
        this.status = status;
    }
}
