package com.example.Consistify.DTO;

public class WatchTimeRequest {

    private String videoId;
    private String title;
    private int watchedSeconds;

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

    public int getWatchedSeconds() {
        return watchedSeconds;
    }

    public void setWatchedSeconds(int watchedSeconds) {
        this.watchedSeconds = watchedSeconds;
    }
}
