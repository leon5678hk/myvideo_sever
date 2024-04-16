package com.server.server.service;

import com.server.server.object.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    User authenticateUser(String email, String password);
    User createUser(User user);
}