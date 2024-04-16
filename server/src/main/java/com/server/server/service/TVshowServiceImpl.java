package com.server.server.service;

import com.server.server.object.TVshow;
import com.server.server.repository.TVshowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TVshowServiceImpl implements TVshowService {

    private final TVshowRepository tvShowRepository;

    @Autowired
    public TVshowServiceImpl(TVshowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    @Override
    public List<TVshow> getTVShows(Boolean isFeatured) {
        if (isFeatured != null) {
            return tvShowRepository.findByIsFeatured(isFeatured);
        }
        return tvShowRepository.findAll();
    }

    @Override
    public TVshow getTVShowById(int id) {
        return tvShowRepository.findById(id).orElse(null);
    }

    @Override
    public TVshow createTVShow(TVshow tvShow) {
        validateTVShow(tvShow); 
        // Find the largest ID to assign a new ID
        List<TVshow> maxIdTVShowList = tvShowRepository.findTopByOrderByIdDesc(Sort.by(Sort.Direction.DESC, "id"));
        int newId = maxIdTVShowList.isEmpty() ? 1 : maxIdTVShowList.get(0).getId() + 1;
        tvShow.setId(newId);
        return tvShowRepository.save(tvShow);
    }

    @Override
    public TVshow updateTVShow(int id, TVshow updatedTvShow) {
        TVshow existingTvShow = tvShowRepository.findById(id).orElse(null);
        if (existingTvShow == null) {
            throw new IllegalArgumentException("TV show not found with ID: " + id);
        }
        validateTVShow(updatedTvShow);
        existingTvShow.setTitle(updatedTvShow.getTitle());
        existingTvShow.setImageUrl(updatedTvShow.getImageUrl());
        existingTvShow.setImdbRating(updatedTvShow.getImdbRating());
        existingTvShow.setIntro(updatedTvShow.getIntro());
        existingTvShow.setStarring(updatedTvShow.getStarring());
        existingTvShow.setGenres(updatedTvShow.getGenres());
        existingTvShow.setSeasons(updatedTvShow.getSeasons());
        existingTvShow.setRentPrice(updatedTvShow.getRentPrice());
        existingTvShow.setPurchasePrice(updatedTvShow.getPurchasePrice());
        existingTvShow.setis_featured(updatedTvShow.getis_featured());
        return tvShowRepository.save(existingTvShow);
    }

    @Override
    public void deleteTVShow(int id) {
        tvShowRepository.deleteById(id);
    }

    @Override
    public List<TVshow> searchTVshows(String query) {
        return tvShowRepository.findByTitleContainingIgnoreCase(query);
    }

    private void validateTVShow(TVshow tvShow) {
        if (tvShow.getTitle() == null || tvShow.getTitle().isEmpty() ||
            tvShow.getImageUrl() == null || tvShow.getImageUrl().isEmpty() ||
            tvShow.getIntro() == null || tvShow.getIntro().isEmpty() ||
            tvShow.getGenres() == null || tvShow.getGenres().isEmpty() ||
            tvShow.getStarring() == null || tvShow.getStarring().isEmpty()) {
            throw new IllegalArgumentException("All fields (title, imageUrl, intro, genres, starring,IMDb rating, rent price, purchase price, seasons) must be provided and not empty.");
        }
        
        Double imdbRating = tvShow.getImdbRating();
        if (imdbRating  == null || tvShow.getRentPrice() < 0 ||
            tvShow.getPurchasePrice() < 0 || tvShow.getSeasons() < 0) {
            throw new IllegalArgumentException("IMDb rating, rent price, purchase price, and seasons must be provided and non-negative.");
        }
    
        if (tvShowRepository.findByTitle(tvShow.getTitle()) != null) {
            throw new IllegalArgumentException("TV show with title '" + tvShow.getTitle() + "' already exists.");
        }
    }
}
