package com.example.Streaker.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "youtube_daily_watch",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "watch_date"})
        }
)
public class YoutubeDailyWatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "watch_date", nullable = false)
    private LocalDate watchDate;

    @Column(name = "watched_seconds", nullable = false)
    private int watchedSeconds;

    @Column(name = "last_position_seconds")
    private int lastPositionSeconds;

    // ✅ Required by JPA
    public YoutubeDailyWatch() {
    }

    // ---------- Getters & Setters ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(LocalDate watchDate) {
        this.watchDate = watchDate;
    }

    public int getWatchedSeconds() {
        return watchedSeconds;
    }

    public void setWatchedSeconds(int watchedSeconds) {
        this.watchedSeconds = watchedSeconds;
    }

    // ✅ THIS WAS MISSING
    public int getLastPositionSeconds() {
        return lastPositionSeconds;
    }

    // ✅ THIS WAS EMPTY BEFORE
    public void setLastPositionSeconds(int lastPositionSeconds) {
        this.lastPositionSeconds = lastPositionSeconds;
    }
}
