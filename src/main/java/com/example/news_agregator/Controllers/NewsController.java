package com.example.news_agregator.Controllers;

import com.example.news_agregator.DTOs.NewsRequestDTO;
import com.example.news_agregator.Entities.RequestType;
import com.example.news_agregator.Services.NewsApiService;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ConcurrentModificationException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsApiService newsApiService;
    @PostMapping
    public ResponseEntity<?> getEverything(@RequestBody NewsRequestDTO dto) throws ExecutionException, InterruptedException {
        ArticleResponse response;
        try {
            response = newsApiService.getNews(dto).get();
            return ResponseEntity.ok(response);
        }
        catch (ConcurrentModificationException e){
            response = newsApiService.getNews(dto).get();
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
