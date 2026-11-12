package com.example.Consistify.Repo;

import com.example.Consistify.Entity.YoutubeVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YoutubeVideoRepository extends JpaRepository<YoutubeVideo, Long> {

    Optional<YoutubeVideo> findTopByUserIdOrderByCreatedAtDesc(Long userId);
}
