package com.example.news_agregator.DTOs;

public record AuthenticationResponse(
        String message,
        String token,
        String username,
        String email,
        String createdAt
) {
}
