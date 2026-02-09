package com.example.Consistify.Service;

import com.example.Consistify.DTO.YoutubeVideoResponse;
import com.example.Consistify.Entity.YoutubeVideo;
import com.example.Consistify.Repo.YoutubeVideoRepository;
import org.springframework.stereotype.Service;

@Service
public class YoutubeVideoService {

    private final YoutubeVideoRepository repository;

    public YoutubeVideoService(YoutubeVideoRepository repository) {
        this.repository = repository;
    }

    public void saveVideo(String youtubeUrl) {

        String videoId = extractVideoId(youtubeUrl);

        YoutubeVideo video = new YoutubeVideo();
        video.setUserId(1L); // hardcode user for now
        video.setVideoId(videoId);

        repository.save(video);
    }

    public YoutubeVideoResponse getCurrentVideo() {

        YoutubeVideo video = repository
                .findTopByUserIdOrderByCreatedAtDesc(1L)
                .orElseThrow(() ->
                        new RuntimeException("No video found"));

        return new YoutubeVideoResponse(video.getVideoId());
    }

    //  YouTube URL → videoId
    private String extractVideoId(String url) {

        if (url.contains("watch?v=")) {
            return url.substring(url.indexOf("v=") + 2, url.indexOf("v=") + 13);
        }

        if (url.contains("youtu.be/")) {
            return url.substring(url.indexOf("youtu.be/") + 9,
                    url.indexOf("youtu.be/") + 20);
        }

        throw new IllegalArgumentException("Invalid YouTube URL");
    }
}

