package com.server.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Sort;

import com.server.server.object.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(int id);
    User findByEmail(String email);

    @Query(value = "{}", sort = "{ 'id' : -1 }")
    List<User> findTopByOrderByIdDesc(Sort sort);
}