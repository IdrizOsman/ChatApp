package com.example.chatapp.service.impl;

import com.example.chatapp.model.User;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  // Add this import for password hashing
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Password encoder for hashing passwords

    // Constructor to inject UserRepository and PasswordEncoder
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
public User getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);  // Return null if not found
}

    @Override
    public User createUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt password before saving
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found."));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        throw new IllegalStateException("No authenticated user");
    }

    @Override
    public User authenticateUser(String username, String password) {
        // Retrieve the user from the database by username
        User user = userRepository.findByUsername(username).orElse(null);

        // Check if the user exists and if the passwords match (after encoding the stored password)
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // If passwords match, return the user
        }

        return null; // If user is null or passwords don't match, return null
    }

    // Add this method for hashing passwords outside of createUser
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
