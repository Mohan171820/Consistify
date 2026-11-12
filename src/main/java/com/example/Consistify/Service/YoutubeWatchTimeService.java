package com.example.Consistify.Service;

import com.example.Consistify.DTO.YoutubeHistoryResponse;
import com.example.Consistify.Entity.YoutubeDailyWatch;
import com.example.Consistify.Entity.YoutubeWatchHistory;
import com.example.Consistify.Repo.YoutubeDailyWatchRepository;
import com.example.Consistify.Repo.YoutubeHistoryRepository;
import com.example.Consistify.Repo.UserRepository;
import com.example.Consistify.Entity.User;
import com.example.Consistify.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeWatchTimeService {

    private final YoutubeDailyWatchRepository dailyRepo;
    private final YoutubeHistoryRepository historyRepo;
    private final UserRepository userRepository;

    public void addWatchTime(String videoId, String title, int seconds) {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getId();
        LocalDate today = LocalDate.now();

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

    public List<YoutubeHistoryResponse> getHistory() {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long userId = user.getId();

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