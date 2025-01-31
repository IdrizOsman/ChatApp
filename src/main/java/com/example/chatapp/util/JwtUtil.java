package com.example.chatapp.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import com.example.chatapp.model.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    // Generate a secure secret key (256-bit key) for HMAC-SHA256
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Method to generate a token
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Token expires in 1 hour
                .signWith(SECRET_KEY)
                .compact();
    }

    // Method to parse the claims from a JWT token
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)  // Use the secure key for parsing
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Method to extract the username from the token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Method to check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Method to validate the token
    public boolean validateToken(String token, User user) {
        return (user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
