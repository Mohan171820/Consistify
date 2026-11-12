package com.example.Consistify.Repo;

import com.example.Consistify.Entity.YoutubeDailyWatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface YoutubeDailyWatchRepository extends JpaRepository<YoutubeDailyWatch, Long> {

    Optional<YoutubeDailyWatch>
    findByUserIdAndWatchDate(Long userId, LocalDate watchDate);
}

