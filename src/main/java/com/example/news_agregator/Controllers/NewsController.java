package com.example.news_agregator.Controllers;

import com.example.news_agregator.DTOs.NewsRequestDTO;
import com.example.news_agregator.Services.NewsApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsApiService newsApiService;
    @PostMapping
    public ResponseEntity<?> getNews(@RequestBody NewsRequestDTO dto) {
        try {
            return ResponseEntity.ok(newsApiService.getNews(dto).get());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
