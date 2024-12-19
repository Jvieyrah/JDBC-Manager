package com.betrybe.podcastmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.betrybe.podcastmanager.entity.Podcast;
import com.betrybe.podcastmanager.exception.PodcastNotFoundException;
import com.betrybe.podcastmanager.repository.PodcastRepository;
import com.betrybe.podcastmanager.service.PodcastService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // Ativamos o perfil de teste
public class PodcastServiceTest {

    @Autowired
    PodcastService podcastService;

    @MockBean
    PodcastRepository podcastRepository;  // Mockamos o bean do repositório

    @Test
    public void testPodcastRetrieval() {
        // Criamos um objeto que será o retorno do repositório mockado
        Podcast podcast = new Podcast();
        podcast.setId(321L);
        podcast.setName("Conversando sobre Java");
        podcast.setUrl("http://www.dominio.com.br/podcast");

        // Mockamos o método 'findById' do repositório para que ele
        // retorne o nosso objeto (dentro de um Optional)
        Mockito.when(podcastRepository.findById(eq(321L)))
            .thenReturn(Optional.of(podcast));

        // Chamamos a camada de serviço e guardamos o retorno
        Podcast returnedPodcast = podcastService.getPodcast(321L);

        // Validamos se o método do repositório foi realmente chamado com o ID que passamos
        Mockito.verify(podcastRepository).findById(eq(321L));

        // Verificamos se os dados retornados estão corretos
        assertEquals(returnedPodcast.getId(), podcast.getId());
        assertEquals(returnedPodcast.getName(), podcast.getName());
        assertEquals(returnedPodcast.getUrl(), podcast.getUrl());
    }

    @Test
    public void testPodcastRetrievalNotFound() {
        // Mockamos o método 'findById' do repositório para que ele retorne
        // um Optional vazio (que vai gerar a exceção no service)
        Mockito.when(podcastRepository.findById(any()))
            .thenReturn(Optional.empty());

        // Chamamos a camada de serviço e validamos que a exceção foi lançada
        assertThrows(PodcastNotFoundException.class, () -> podcastService.getPodcast(456L));

        // Validamos se o método do repositório foi realmente chamado com o ID que passamos
        Mockito.verify(podcastRepository).findById(eq(456L));
    }

    @Test
    public void testPodcastCreation() {
        // Primeiro criamos um objeto que utilizaremos para salvar
        Podcast podcast = new Podcast();
        podcast.setName("Conversando sobre Java");
        podcast.setUrl("http://www.dominio.com.br/podcast");

        // Criamos um objeto que será o retorno do repositório mockado
        Podcast podcastToReturn = new Podcast();
        podcastToReturn.setId(123L);
        podcastToReturn.setName(podcast.getName());
        podcastToReturn.setUrl(podcast.getUrl());

        // Mockamos o método 'save' do repositório para que ele retorne o nosso objeto
        Mockito.when(podcastRepository.save(any(Podcast.class)))
            .thenReturn(podcastToReturn);

        // Chamamos a camada de serviço e guardamos o retorno
        Podcast savedPodcast = podcastService.createPodcast(podcast);

        // Validamos se o método do repositório foi realmente chamado com qualquer valor
        Mockito.verify(podcastRepository).save(any(Podcast.class));

        // Verificamos se os dados retornados estão corretos
        assertEquals(123L, savedPodcast.getId());
        assertEquals(podcast.getName(), savedPodcast.getName());
        assertEquals(podcast.getUrl(), savedPodcast.getUrl());
    }
}