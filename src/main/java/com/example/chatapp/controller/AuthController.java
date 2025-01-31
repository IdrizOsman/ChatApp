package com.example.chatapp.controller;

import com.example.chatapp.model.User;
import com.example.chatapp.service.UserService;
import com.example.chatapp.util.JwtUtil;  // Utility class for JWT generation
import com.example.chatapp.dto.JwtResponse; // Import JwtResponse class
import com.example.chatapp.dto.LoginRequest; // Import LoginRequest class
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;  // Inject JWT utility class

    // Constructor-based dependency injection
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Call the authenticateUser method from UserService
        User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            // Generate JWT token upon successful login
            String token = jwtUtil.generateToken(user); // Generate token with user info
            return ResponseEntity.ok(new JwtResponse(token)); // Return the token in the response
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // Register endpoint
    @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody User user) {
    try {
        // Log the incoming request to see if it's being received
        System.out.println("Registering user: " + user.getUsername());

        // Check if the username already exists
        if (userService.getUserByUsername(user.getUsername()) != null) {
            System.out.println("Username already exists");
            return ResponseEntity.status(400).body("Username already exists");
        }

        // Save the new user
        User newUser = userService.createUser(user);
        System.out.println("User created successfully: " + newUser.getUsername());

        return ResponseEntity.status(201).body(newUser); // Return the created user details
    } catch (Exception e) {
        // Log the exception to see if it's a specific error
        System.err.println("Error during registration: " + e.getMessage());
        return ResponseEntity.status(500).body("An error occurred while registering the user.");
    }
}
}
