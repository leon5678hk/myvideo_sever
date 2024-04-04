package com.server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.server.server.object.User;
import com.server.server.repository.UserRepository;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Initialize the passwordEncoder
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody User credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        if (email != null && password != null) {
            // Authentication logic
            User user = userRepository.findByEmail(email);
            if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }
            return ResponseEntity.ok(user); // Return the user object
        } else {
            return ResponseEntity.badRequest().body("Email and password are required");
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Validated @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid user data"));
        }

        // Check if the email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Email already exists"));
        }

        // Get the maximum ID from the database
        List<User> maxIdUserList = userRepository.findTopByOrderByIdDesc(Sort.by(Sort.Direction.DESC, "id"));
        int newId = maxIdUserList.isEmpty() ? 1 : maxIdUserList.get(0).getId() + 1;

        // Set the incremented ID to the user
        user.setId(newId);
        // Encode the password

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // Save the user

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

}
