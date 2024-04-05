package com.server.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Sort;
import com.server.server.object.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Optional<Movie> findById(int id);

    Movie findByTitle(String title);

    @Query("{'is_featured': ?0}")
    List<Movie> findByIsFeatured(boolean is_featured);

    Movie save(Movie movie);

    void deleteById(int id);

    @Query(value = "{}", sort = "{ 'id' : -1 }")
    List<Movie> findTopByOrderByIdDesc(Sort sort);
}