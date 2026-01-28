package com.example.Streaker.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class YoutubeWatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String videoId;
    private String title;

    private long watchedSeconds;

    private LocalDateTime lastWatchedAt;

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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getWatchedSeconds() {
        return watchedSeconds;
    }

    public void setWatchedSeconds(long watchedSeconds) {
        this.watchedSeconds = watchedSeconds;
    }

    public LocalDateTime getLastWatchedAt() {
        return lastWatchedAt;
    }

    public void setLastWatchedAt(LocalDateTime lastWatchedAt) {
        this.lastWatchedAt = lastWatchedAt;
    }
// getters & setters
}
