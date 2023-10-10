package com.example.news_agregator.DTOs;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class PostDTO {
    private String description;
    private Integer likes = 0;
    private Integer comments = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
}
