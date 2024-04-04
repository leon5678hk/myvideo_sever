package com.server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import com.server.server.object.TVshow;
import com.server.server.repository.TVshowRepository;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tvshows") // Base URI for all endpoints in this controller
public class TVshowController {

    private final TVshowRepository tvShowRepository;

    @Autowired
    public TVshowController(TVshowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    //Get TV shows(all,featured)
    @GetMapping
    public List<TVshow> getMovies(@RequestParam(required = false) Boolean isFeatured) {
        if (isFeatured != null) {
            return tvShowRepository.findByIsFeatured(isFeatured);
        }
        return tvShowRepository.findAll();
    }
    
    // Get a TV show by its integer id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTVShowById(@PathVariable int id) {
        TVshow tvShow = tvShowRepository.findById(id).orElse(null);
        if (tvShow == null) {
            return ResponseEntity.badRequest().body("Invalid TV show data");
        }
        return ResponseEntity.ok(tvShow);
    }

    // Create a new TV show
    @PostMapping
    public ResponseEntity<?> createTVShow(@RequestBody TVshow tvShow) {
    if (tvShowRepository.findByTitle(tvShow.getTitle()) != null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TV show with title '" + tvShow.getTitle() + "' already exists.");
    }

    List<TVshow> maxIdTVShowList = tvShowRepository.findTopByOrderByIdDesc(Sort.by(Sort.Direction.DESC, "id"));
    int newId = maxIdTVShowList.isEmpty() ? 1 : maxIdTVShowList.get(0).getId() + 1;

    // Set the incremented id to the TV show
    tvShow.setId(newId);

    // Save the TV show
    return ResponseEntity.ok(tvShowRepository.save(tvShow));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTvShow(@PathVariable int id, @RequestBody TVshow updatedTvShow) {
        TVshow existingtvShow = tvShowRepository.findById(id).orElse(null);
        if (existingtvShow == null) {
            return ResponseEntity.badRequest().body("TV show not found with ID: " + id);
        }

        // Check for missing data
        if (updatedTvShow.getTitle() == null || updatedTvShow.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Title is required for updating the TV show.");
        }

        // Update the existing TV show with the provided data
        existingtvShow.setTitle(updatedTvShow.getTitle());
        existingtvShow.setImageUrl(updatedTvShow.getImageUrl());
        existingtvShow.setImdbRating(updatedTvShow.getImdbRating());
        existingtvShow.setIntro(updatedTvShow.getIntro());
        existingtvShow.setStarring(updatedTvShow.getStarring());
        existingtvShow.setGenres(updatedTvShow.getGenres());
        existingtvShow.setSeasons(updatedTvShow.getSeasons());
        existingtvShow.setRentPrice(updatedTvShow.getRentPrice());
        existingtvShow.setPurchasePrice(updatedTvShow.getPurchasePrice());
        existingtvShow.setis_featured(updatedTvShow.getis_featured());

        // Save the updated TV show to the database
        TVshow savedTVshow = tvShowRepository.save(existingtvShow);

        tvShowRepository.save(existingtvShow);
        return ResponseEntity.ok(savedTVshow);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTvShow(@PathVariable int id) {
        TVshow existingtvShow = tvShowRepository.findById(id).orElse(null);
        if (existingtvShow == null) {
            return ResponseEntity.badRequest().body("TV show not found with ID: " + id);
        }
        tvShowRepository.deleteById(id);
        return ResponseEntity.ok("TV show with ID: " + id + " deleted successfully.");
    }


}