package com.server.server.service;

import com.server.server.object.Movie;
import com.server.server.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getMovies(Boolean isFeatured) {
        if (isFeatured != null) {
            return movieRepository.findByIsFeatured(isFeatured);
        }
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(int id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie createMovie(Movie movie) {
        validateMovie(movie); 
        movie.setType("movie");
        List<Movie> maxIdMovieList = movieRepository.findTopByOrderByIdDesc(Sort.by(Sort.Direction.DESC, "id"));
        int newId = maxIdMovieList.isEmpty() ? 1 : maxIdMovieList.get(0).getId() + 1;
        movie.setId(newId);
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(int id, Movie updatedMovie) {
        Movie existingMovie = movieRepository.findById(id).orElse(null);
        if (existingMovie == null) {
            throw new IllegalArgumentException("Movie not found with ID: " + id);
        }
        validateMovie(updatedMovie); 
        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setImageUrl(updatedMovie.getImageUrl());
        existingMovie.setImdbRating(updatedMovie.getImdbRating());
        existingMovie.setIntro(updatedMovie.getIntro());
        existingMovie.setStarring(updatedMovie.getStarring());
        existingMovie.setGenres(updatedMovie.getGenres());
        existingMovie.setDuration(updatedMovie.getDuration());
        existingMovie.setRentPrice(updatedMovie.getRentPrice());
        existingMovie.setPurchasePrice(updatedMovie.getPurchasePrice());
        existingMovie.setis_featured(updatedMovie.getis_featured());
        return movieRepository.save(existingMovie);
    }

    @Override
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> searchMovies(String query) {
        return movieRepository.findByTitleContainingIgnoreCase(query);
    }

    private void validateMovie(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty() ||
            movie.getImageUrl() == null || movie.getImageUrl().isEmpty() ||
            movie.getIntro() == null || movie.getIntro().isEmpty() ||
            movie.getGenres() == null || movie.getGenres().isEmpty() ||
            movie.getStarring() == null || movie.getStarring().isEmpty()) {
            throw new IllegalArgumentException("All fields (title, imageUrl, intro, genres, starring,IMDb rating, duration, rent price, purchase price) must be provided and not empty.");
        }

        if (movie.getImdbRating() == 0.0 || movie.getDuration() < 0 ||
            movie.getRentPrice() < 0 || movie.getPurchasePrice() < 0) {
            throw new IllegalArgumentException("IMDb rating, duration, rent price, and purchase price must be provided and non-negative.");
        }

        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            throw new IllegalArgumentException("Movie with title '" + movie.getTitle() + "' already exists.");
        }
    }
}
