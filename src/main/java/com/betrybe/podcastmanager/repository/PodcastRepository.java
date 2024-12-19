package com.betrybe.podcastmanager.repository;

import com.betrybe.podcastmanager.entity.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {

}
