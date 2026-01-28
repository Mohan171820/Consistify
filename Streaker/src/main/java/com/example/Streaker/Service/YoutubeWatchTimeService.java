package com.example.Streaker.Service;

import com.example.Streaker.DTO.YoutubeHistoryResponse;
import com.example.Streaker.Entity.YoutubeDailyWatch;
import com.example.Streaker.Entity.YoutubeWatchHistory;
import com.example.Streaker.Repo.YoutubeDailyWatchRepository;
import com.example.Streaker.Repo.YoutubeHistoryRepository;
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

    // 🔥 CALLED WHEN WATCH TIME IS SENT
    public void addWatchTime(String videoId, String title, int seconds) {

        Long userId = 1L; // temp user
        LocalDate today = LocalDate.now();

        // ---------- DAILY TOTAL ----------
        YoutubeDailyWatch daily =
                dailyRepo.findByUserIdAndWatchDate(userId, today)
                        .orElseGet(() -> {
                            YoutubeDailyWatch d = new YoutubeDailyWatch();
                            d.setUserId(userId);
                            d.setWatchDate(today);
                            d.setWatchedSeconds(0);
                            return d;
                        });

        daily.setWatchedSeconds(daily.getWatchedSeconds() + seconds);
        dailyRepo.save(daily);

        // ---------- HISTORY PER VIDEO ----------
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

        history.setWatchedSeconds(history.getWatchedSeconds() + seconds);
        history.setLastWatchedAt(LocalDateTime.now());

        historyRepo.save(history);
    }

    // 🔁 FETCH HISTORY
    public List<YoutubeHistoryResponse> getHistory() {

        Long userId = 1L;

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
