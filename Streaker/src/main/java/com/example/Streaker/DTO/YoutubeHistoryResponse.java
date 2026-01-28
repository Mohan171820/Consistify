package com.example.Streaker.DTO;


public class YoutubeHistoryResponse {

    private String videoId;
    private String title;
    private long watchedSeconds;

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
// getters & setters
}
