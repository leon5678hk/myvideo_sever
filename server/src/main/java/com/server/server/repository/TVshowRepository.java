package com.server.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Sort;
import com.server.server.object.TVshow;
import java.util.List;
import java.util.Optional;

//managing tvshow data in MongoDB
public interface TVshowRepository extends MongoRepository<TVshow, String> {
    Optional<TVshow> findById(int id);

    TVshow findByTitle(String title);

    @Query("{'is_featured': ?0}")
    List<TVshow> findByIsFeatured(boolean is_featured);

    TVshow save(TVshow movie);

    void deleteById(int id);

    @Query(value = "{}", sort = "{ 'id' : -1 }")
    List<TVshow> findTopByOrderByIdDesc(Sort sort);

    List<TVshow> findByTitleContainingIgnoreCase(String title);

}