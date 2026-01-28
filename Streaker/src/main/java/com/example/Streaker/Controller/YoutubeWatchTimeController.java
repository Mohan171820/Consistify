package com.example.Streaker.Controller;

import com.example.Streaker.DTO.WatchTimeRequest;
import com.example.Streaker.DTO.YoutubeHistoryResponse;
import com.example.Streaker.Service.YoutubeWatchTimeService;
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

    // ---------- WATCH TIME ----------
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

    // ---------- HISTORY ----------
    @GetMapping("/history")
    public ResponseEntity<List<YoutubeHistoryResponse>> history() {
        return ResponseEntity.ok(watchTimeService.getHistory());
    }
}
