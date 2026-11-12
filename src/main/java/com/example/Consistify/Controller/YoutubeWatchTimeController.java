package com.example.Consistify.Controller;

import com.example.Consistify.DTO.WatchTimeRequest;
import com.example.Consistify.DTO.YoutubeHistoryResponse;
import com.example.Consistify.Service.YoutubeWatchTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/youtube")
public class YoutubeWatchTimeController {

    private final YoutubeWatchTimeService watchTimeService;

    public YoutubeWatchTimeController(YoutubeWatchTimeService watchTimeService) {
        this.watchTimeService = watchTimeService;
    }

    //Records the watch time for a YouTube video.
    // This endpoint is called periodically to track how long the user has watched a specific video.
    @PostMapping("/watch-time")
    public ResponseEntity<Void> addWatchTime(
            @RequestBody WatchTimeRequest request) {

        watchTimeService.addWatchTime(
                request.getVideoId(),
                request.getTitle(),
                request.getWatchedSeconds()
        );

        return ResponseEntity.ok().build();
    }

    //By this mapping we can get the previous video the user had watched before
    @GetMapping("/history")
    public ResponseEntity<List<YoutubeHistoryResponse>> history() {
        return ResponseEntity.ok(watchTimeService.getHistory());
    }
}
