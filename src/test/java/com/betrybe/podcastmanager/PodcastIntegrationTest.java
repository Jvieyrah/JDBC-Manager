package com.betrybe.podcastmanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.betrybe.podcastmanager.entity.Podcast;
import com.betrybe.podcastmanager.repository.PodcastRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers // Configuramos o teste para utilizar Testcontainers
public class PodcastIntegrationTest {

  // Cria um container MySQL para ser utilizado nos testes
  @Container
  public static MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:8.0.29")
      .withDatabaseName("podcast");

  // Configuramos dinamicamente as propriedades do datasource
  @DynamicPropertySource
  public static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
  }

  @Autowired
  MockMvc mockMvc;

  @Autowired
  PodcastRepository podcastRepository;

  @Test
  public void testGetPodcast() throws Exception {
    // Crie um podcast e salve no banco de dados
    Podcast podcast = new Podcast();
    podcast.setName("Conversando sobre Java");
    podcast.setUrl("http://www.dominio.com.br/podcast");

    Long createdId = podcastRepository.save(podcast).getId();

    String podcastUrl = "/podcasts/%s".formatted(createdId);

    // Faça uma requisição GET para /podcasts/{id} e verifique se os dados retornados estão corretos
    mockMvc.perform(get(podcastUrl)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(createdId))
        .andExpect(jsonPath("$.name").value("Conversando sobre Java"))
        .andExpect(jsonPath("$.url").value("http://www.dominio.com.br/podcast"));
  }
}
