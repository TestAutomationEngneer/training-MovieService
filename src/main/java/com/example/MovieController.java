package com.example;

import com.example.exception.NotFoundException;
import com.example.model.Movie;
import com.example.model.MovieDTO;
import com.example.service.MovieService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Get("/")
    public List<MovieDTO> getAll() {
        return movieService.getAllMovies();
    }

    @Get("/{id}")
    public MovieDTO getOne(@PathVariable Long id) {
        return movieService.getMovieById(id);

    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    public Movie create(@Body Movie movie) {
        return movieService.saveMovie(movie);
    }



    @Delete("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return HttpStatus.NO_CONTENT;
    }
}
