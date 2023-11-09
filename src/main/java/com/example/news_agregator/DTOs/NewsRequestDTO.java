package com.example.news_agregator.DTOs;

import com.example.news_agregator.Entities.RequestType;
import lombok.Data;

@Data
public class NewsRequestDTO {
    private String keyphrase;
    private String category;
    private String sortBy = "publishedAt";
    private String language = "ru";
    private RequestType type;
    private int page = 1;
}
