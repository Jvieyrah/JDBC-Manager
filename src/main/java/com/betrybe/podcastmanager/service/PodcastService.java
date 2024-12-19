package com.betrybe.podcastmanager.service;

import com.betrybe.podcastmanager.entity.Podcast;
import com.betrybe.podcastmanager.exception.PodcastNotFoundException;
import com.betrybe.podcastmanager.repository.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodcastService {

  private final PodcastRepository podcastRepository;

  @Autowired
  public PodcastService(PodcastRepository podcastRepository) {
    this.podcastRepository = podcastRepository;
  }

  public Podcast getPodcast(Long id) {
    return podcastRepository.findById(id)
        .orElseThrow(PodcastNotFoundException::new);
  }

  public Podcast createPodcast(Podcast podcast) {
    return podcastRepository.save(podcast);
  }
  
}
