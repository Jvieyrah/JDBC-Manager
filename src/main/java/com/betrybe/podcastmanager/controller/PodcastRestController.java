package com.betrybe.podcastmanager.controller;

import com.betrybe.podcastmanager.dto.PodcastDto;
import com.betrybe.podcastmanager.entity.Podcast;
import com.betrybe.podcastmanager.service.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/podcasts")
public class PodcastRestController {

  private PodcastService podcastService;

  @Autowired
  public PodcastRestController(PodcastService podcastService) {
    this.podcastService = podcastService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<PodcastDto> getPodcast(@PathVariable Long id) {
    Podcast podcast = podcastService.getPodcast(id);

    PodcastDto podcastDto = new PodcastDto(
        podcast.getId(), podcast.getName(), podcast.getUrl()
    );

    return ResponseEntity.ok(podcastDto);
  }

  @PostMapping
  public ResponseEntity<PodcastDto> createPodcast(@RequestBody PodcastDto newPodcastDto) {
    Podcast podcast = new Podcast();
    podcast.setName(newPodcastDto.name());
    podcast.setUrl(newPodcastDto.url());

    Podcast savedPodcast = podcastService.createPodcast(podcast);

    PodcastDto podcastDto = new PodcastDto(
        savedPodcast.getId(), savedPodcast.getName(), savedPodcast.getUrl()
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(podcastDto);
  }
}
