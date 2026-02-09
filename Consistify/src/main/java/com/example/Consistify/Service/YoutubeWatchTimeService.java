package com.example.Consistify.Service;

import com.example.Consistify.DTO.YoutubeHistoryResponse;
import com.example.Consistify.Entity.YoutubeDailyWatch;
import com.example.Consistify.Entity.YoutubeWatchHistory;
import com.example.Consistify.Repo.YoutubeDailyWatchRepository;
import com.example.Consistify.Repo.YoutubeHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class YoutubeWatchTimeService {

    private final YoutubeDailyWatchRepository dailyRepo;
    private final YoutubeHistoryRepository historyRepo;

    public YoutubeWatchTimeService(
            YoutubeDailyWatchRepository dailyRepo,
            YoutubeHistoryRepository historyRepo) {
        this.dailyRepo = dailyRepo;
        this.historyRepo = historyRepo;
    }

    // This method is called whenever watch time is sent from the frontend
    // It updates both daily total watch time and per-video watch history
    public void addWatchTime(String videoId, String title, int seconds) {

        Long userId = 1L; // temporary user (will be dynamic later)
        LocalDate today = LocalDate.now();

        // ---------- DAILY TOTAL ----------
        // Get today's watch record for the user
        // If not present, create a new daily record
        YoutubeDailyWatch daily =
                dailyRepo.findByUserIdAndWatchDate(userId, today)
                        .orElseGet(() -> {
                            YoutubeDailyWatch d = new YoutubeDailyWatch();
                            d.setUserId(userId);
                            d.setWatchDate(today);
                            d.setWatchedSeconds(0);
                            return d;
                        });

        // Add the new watched seconds to today's total
        daily.setWatchedSeconds(daily.getWatchedSeconds() + seconds);
        dailyRepo.save(daily);

        // ---------- HISTORY PER VIDEO ----------
        // Get watch history for this specific video
        // If the video was not watched before, create a new history record
        YoutubeWatchHistory history =
                historyRepo.findByUserIdAndVideoId(userId, videoId)
                        .orElseGet(() -> {
                            YoutubeWatchHistory h = new YoutubeWatchHistory();
                            h.setUserId(userId);
                            h.setVideoId(videoId);
                            h.setTitle(title);
                            h.setWatchedSeconds(0);
                            return h;
                        });

        // Update total watch time and last watched timestamp for the video
        history.setWatchedSeconds(history.getWatchedSeconds() + seconds);
        history.setLastWatchedAt(LocalDateTime.now());

        historyRepo.save(history);
    }

    // This method is used to fetch the watch history of the user
    // Videos are returned in descending order of last watched time
    public List<YoutubeHistoryResponse> getHistory() {

        Long userId = 1L; // temporary user

        return historyRepo.findAllByUserIdOrderByLastWatchedAtDesc(userId)
                .stream()
                .map(h -> {
                    YoutubeHistoryResponse dto = new YoutubeHistoryResponse();
                    dto.setVideoId(h.getVideoId());
                    dto.setTitle(h.getTitle());
                    dto.setWatchedSeconds(h.getWatchedSeconds());
                    return dto;
                })
                .toList();
    }
}
