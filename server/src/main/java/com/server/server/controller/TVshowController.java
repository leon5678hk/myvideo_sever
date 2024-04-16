package com.server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.server.server.object.TVshow;
import com.server.server.service.TVshowService;

@CrossOrigin
@RestController
@RequestMapping("/tvshows")
public class TVshowController {

    private final TVshowService tvShowService;

    @Autowired
    public TVshowController(TVshowService tvShowService) {
        this.tvShowService = tvShowService;
    }

    @GetMapping
    public ResponseEntity<?> getTVShows(@RequestParam(required = false) Boolean isFeatured) {
        return ResponseEntity.ok(tvShowService.getTVShows(isFeatured));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTVShowById(@PathVariable int id) {
        TVshow tvShow = tvShowService.getTVShowById(id);
        if (tvShow == null) {
            return ResponseEntity.badRequest().body("Invalid TV show ID");
        }
        return ResponseEntity.ok(tvShow);
    }

    @PostMapping
    public ResponseEntity<?> createTVShow(@RequestBody TVshow tvShow) {
        try {
            return ResponseEntity.ok(tvShowService.createTVShow(tvShow));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTVShow(@PathVariable int id, @RequestBody TVshow updatedTvShow) {
        try {
            return ResponseEntity.ok(tvShowService.updateTVShow(id, updatedTvShow));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTVShow(@PathVariable int id) {
        try {
            tvShowService.deleteTVShow(id);
            return ResponseEntity.ok("TV show with ID: " + id + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("TV show not found with ID: " + id);
        }
    }
}