package com.example;

import com.example.model.MovieDTO;
import com.example.repository.MovieRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class MovieApiTest {

    @Inject
    MovieRepository movieRepository;

    @Test
    void shouldGetAllActors() {
      MovieDTO movie1 = new MovieDTO();
      MovieDTO movie2 = new MovieDTO();
      MovieDTO movie3 = new MovieDTO();

        List<MovieDTO> movies = List.of(movie1, movie2, movie3);
        assertThat(movies).hasSize(3);
    }

    @Test
    void shouldLoadActorsRepository() {
        var loadedMovies = movieRepository.getMovieList();
        assertThat(loadedMovies).hasSize(5);
    }
}
