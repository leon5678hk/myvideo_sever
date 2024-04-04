package com.server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.server.server.object.Movie;
import com.server.server.repository.MovieRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/movies") // Base URI for all endpoints in this controller
public class MovieController {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Get Movies(all,featured)
    @GetMapping
    public List<Movie> getMovies(@RequestParam(required = false) Boolean isFeatured) {
        if (isFeatured != null) {
            return movieRepository.findByIsFeatured(isFeatured);
        }
        return movieRepository.findAll();
    }

    // Get a movie by its integer id
    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable int id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return ResponseEntity.badRequest().body("Invalid movie data");
        }
        return ResponseEntity.ok(movie);
    }

    // Create a new movie
    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {        // Check if a movie with the same title already exists
        if (movieRepository.findByTitle(movie.getTitle()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie with title '" + movie.getTitle() + "' already exists.");
        }

        // Find the largest id from the existing movies
        List<Movie> maxIdMovieList = movieRepository.findTopByOrderByIdDesc(Sort.by(Sort.Direction.DESC, "id"));
        int newId = maxIdMovieList.isEmpty() ? 1 : maxIdMovieList.get(0).getId() + 1;

        // Set the incremented id to the movie
        movie.setId(newId);

        // Save the movie
        return ResponseEntity.ok(movieRepository.save(movie));
    }

    // Update an existing movie
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody Movie updatedMovie) {
        // Check if the movie exists
        Movie existingMovie = movieRepository.findById(id).orElse(null);
        if (existingMovie == null) {
            return ResponseEntity.badRequest().body("Movie not found with ID: " + id);
        }

        // Check for missing data
        if (updatedMovie.getTitle() == null || updatedMovie.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Title is required for updating the movie.");
        }

        // Update the existing movie
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

        // Save the updated movie
        Movie savedMovie = movieRepository.save(existingMovie);
        return ResponseEntity.ok(savedMovie);
    }

    // Delete an existing movie
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        // Check if the movie exists
        Movie existingMovie = movieRepository.findById(id).orElse(null);
        if (existingMovie == null) {
            return ResponseEntity.badRequest().body("Movie not found with ID: " + id);
        }

        // Delete the movie
        movieRepository.deleteById(id);
        return ResponseEntity.ok("Movie with ID: " + id + " deleted successfully.");
    }
}