package com.example.news_agregator.Controllers;

import com.example.news_agregator.DTOs.NewsRequestDTO;
import com.example.news_agregator.Entities.RequestType;
import com.example.news_agregator.Services.NewsApiService;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsApiService newsApiService;
    @GetMapping
    public ResponseEntity<ArticleResponse> getEverything(@RequestBody NewsRequestDTO dto) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(newsApiService.getNews(dto).get());
    }
}
