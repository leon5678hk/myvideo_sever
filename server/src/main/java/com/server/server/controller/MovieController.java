package com.server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.server.server.object.Movie;
import com.server.server.service.MovieService;

@CrossOrigin
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getMovies(@RequestParam(required = false) Boolean isFeatured) {
        return ResponseEntity.ok(movieService.getMovies(isFeatured));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable int id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.badRequest().body("Invalid movie data");
        }
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
        try {
            return ResponseEntity.ok(movieService.createMovie(movie));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody Movie updatedMovie) {
        try {
            return ResponseEntity.ok(movieService.updateMovie(id, updatedMovie));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        try {
            movieService.deleteMovie(id);
            return ResponseEntity.ok("Movie with ID: " + id + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Movie not found with ID: " + id);
        }
    }
}
