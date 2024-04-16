package com.server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.server.server.service.MovieService;
import com.server.server.service.TVshowService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {

    private final MovieService movieService;
    private final TVshowService tvShowService;

    @Autowired
    public SearchController(MovieService movieService, TVshowService tvShowService) {
        this.movieService = movieService;
        this.tvShowService = tvShowService;
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Query parameter is missing or empty");
        }

        List<Map<String, Object>> filteredMovies = movieService.searchMovies(query).stream()
            .map(movie -> {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("id", movie.getId());
                movieMap.put("title", movie.getTitle());
                movieMap.put("imageUrl", movie.getImageUrl());
                movieMap.put("type", movie.getType());
                return movieMap;
            })
            .collect(Collectors.toList());

        List<Map<String, Object>> filteredTvShows = tvShowService.searchTVshows(query).stream()
            .map(tvShow -> {
                Map<String, Object> tvShowMap = new HashMap<>();
                tvShowMap.put("id", tvShow.getId());
                tvShowMap.put("title", tvShow.getTitle());
                tvShowMap.put("imageUrl", tvShow.getImageUrl());
                tvShowMap.put("type", tvShow.getType());
                return tvShowMap;
            })
            .collect(Collectors.toList());

        List<Map<String, Object>> combinedResults = Stream.concat(filteredMovies.stream(), filteredTvShows.stream())
                                             .collect(Collectors.toList());

        return ResponseEntity.ok(combinedResults);
    }
}
