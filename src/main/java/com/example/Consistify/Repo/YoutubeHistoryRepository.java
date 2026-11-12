package com.example.Consistify.Repo;

import com.example.Consistify.Entity.YoutubeWatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface YoutubeHistoryRepository
        extends JpaRepository<YoutubeWatchHistory, Long> {

    Optional<YoutubeWatchHistory> findByUserIdAndVideoId(Long userId, String videoId);

    List<YoutubeWatchHistory> findAllByUserIdOrderByLastWatchedAtDesc(Long userId);
}
