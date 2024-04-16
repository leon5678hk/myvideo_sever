package com.server.server.service;

import com.server.server.object.Movie;
import java.util.List;

public interface MovieService {
    List<Movie> getMovies(Boolean isFeatured);
    Movie getMovieById(int id);
    Movie createMovie(Movie movie);
    Movie updateMovie(int id, Movie updatedMovie);
    void deleteMovie(int id);
    List<Movie> searchMovies(String query);
}
