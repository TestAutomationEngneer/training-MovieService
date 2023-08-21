package com.example.repository;

import com.example.model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class MovieRepository {

    private List<Movie> movieList = new ArrayList<>();

    public List<Movie> getMovieList() {
        return movieList;
    }

    @EventListener
    public void onStartup(StartupEvent event) {
        loadMovies();
    }


    public Optional<Movie> findById(Long id) {
        return movieList.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst();
    }

    public List<Movie> findAll() {
        return movieList;
    }

    public Movie save(Movie movie) {
        movie.setId((long) (movieList.size() + 1));
        movieList.add(movie);
        return movie;
    }

    public boolean delete(Long id) {
        return movieList.removeIf(movie -> movie.getId().equals(id));
    }

    private void loadMovies() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Movie>> typeReference = new TypeReference<>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/movies.json");

        try {
            movieList = mapper.readValue(inputStream, typeReference);
            log.info("Movies library loaded successfully!");
        } catch (IOException e) {
            log.error("Unable to load movies: " + e.getMessage());
        }
    }
}
