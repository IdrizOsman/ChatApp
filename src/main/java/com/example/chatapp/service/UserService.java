package com.example.chatapp.service;

import com.example.chatapp.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user); // Уверете се, че този метод е правилно дефиниран
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getCurrentUser(); // Add this method to retrieve the currently logged-in user
    User authenticateUser(String username, String password);
    String hashPassword(String password); // Add this method for password hashing
    
}


