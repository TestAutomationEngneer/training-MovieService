package com.example.service;

import com.example.actor.ActorClient;
import com.example.model.Actor;
import com.example.model.Movie;
import com.example.model.MovieDTO;
import com.example.repository.MovieRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class MovieService {
    @Inject
    private MovieRepository movieRepository;

    @Inject
    private ActorClient actorClient;

    public MovieDTO getMovieById(Long id) {
        MovieDTO movieDTO = null;
        Optional<Movie> movie = movieRepository.findById(id);
        if(movie.isPresent()){
            List<Actor> actors = loadActors(movie.get().getActorIds());
            movieDTO = new MovieDTO();
            movieDTO.setId(movie.get().getId());
            movieDTO.setTitle(movie.get().getTitle());
            movieDTO.setDirector(movie.get().getDirector());
            movieDTO.setActors(actors);
        }
        return movieDTO;
    }



    public List<MovieDTO> getAllMovies() {
        List<MovieDTO> response = new ArrayList<>();
        MovieDTO movieDTO = null;
        List<Movie> movies = movieRepository.findAll();
        for(Movie movie : movies){
            List<Actor> actors = loadActors(movie.getActorIds());
            movieDTO = new MovieDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setDirector(movie.getDirector());
            movieDTO.setActors(actors);
            response.add(movieDTO);
        }
        return response;
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movieRepository.delete(movie.getId());
    }



    private List<Actor> loadActors(List<Long> actorIds) {
        List<Actor> actorList = new ArrayList<>();
        for (Long actorId : actorIds) {
            Actor actor = actorClient.getActorById(actorId);
            if (actor != null) {
                actorList.add(actor);
            }
        }
        return actorList;
    }
}




