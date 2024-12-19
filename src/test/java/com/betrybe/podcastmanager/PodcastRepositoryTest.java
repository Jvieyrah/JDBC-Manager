package com.betrybe.podcastmanager;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.betrybe.podcastmanager.entity.Podcast;
import com.betrybe.podcastmanager.service.PodcastService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.betrybe.podcastmanager.exception.PodcastNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
public class PodcastRepositoryTest {

  @Autowired
  PodcastService podcastService;


  // Testes de integração com o banco de dados mplementação deve testar os métodos getPodcast e createPodcast da classe PodcastService
  // Utilize o banco de dados H2 para os testes

  // Teste o método getPodcast
  @Test
  public void testGetPodcast() {
    // Crie um podcast e salve no banco de dados
    Podcast podcast = new Podcast();
    podcast.setId(321L);
    podcast.setName("Conversando sobre Java");
    podcast.setUrl("http://www.dominio.com.br/podcast");

    Long createdId = podcastService.createPodcast(podcast).getId();

    // Chame o método getPodcast
    Podcast returnedPodcast = podcastService.getPodcast(createdId);

    // Verifique se o podcast retornado é o mesmo que foi salvo no banco de dados
    assert(returnedPodcast.getId().equals(createdId));
    assert(returnedPodcast.getName().equals(podcast.getName()));
    assert(returnedPodcast.getUrl().equals(podcast.getUrl()));

  }

  @Test
  public void testGetPodcastNotFound() {
    // Chame o método getPodcast com um id que não existe no banco de dados
    // Verifique se a exceção PodcastNotFoundException é lançada
    assertThrows(PodcastNotFoundException.class, () -> {
      podcastService.getPodcast(999L);
    });
  }

  @Test
  public void testCreatePodcast() {
    // Crie um podcast e salve no banco de dados
    Podcast podcast = new Podcast();
    podcast.setId(321L);
    podcast.setName("Conversando sobre Java");
    podcast.setUrl("http://www.dominio.com.br/podcast");

    Long createdId = podcastService.createPodcast(podcast).getId();

    // Verifique se o podcast foi salvo corretamente no banco de dados
    Podcast returnedPodcast = podcastService.getPodcast(createdId);

    assert(returnedPodcast.getId().equals(createdId));
    assert(returnedPodcast.getName().equals(podcast.getName()));
    assert(returnedPodcast.getUrl().equals(podcast.getUrl()));
  }

}
