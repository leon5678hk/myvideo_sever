package com.server.server.service;

import com.server.server.object.TVshow;
import java.util.List;

public interface TVshowService {
    List<TVshow> getTVShows(Boolean isFeatured);
    TVshow getTVShowById(int id);
    TVshow createTVShow(TVshow tvShow);
    TVshow updateTVShow(int id, TVshow updatedTvShow);
    void deleteTVShow(int id);
    List<TVshow> searchTVshows(String query);

}