package com.example.news_agregator.DTOs;

public record RegisterRequest (
        String username,
        String email,
        String password
) {
}
