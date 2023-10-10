package com.example.news_agregator.Services;

import com.example.news_agregator.DTOs.NewsRequestDTO;
import com.example.news_agregator.Entities.RequestType;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.SourcesRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NewsApiService {
    private final NewsApiClient newsApiClient = new NewsApiClient("8444ad820f4141c097124e807267358d");
    public CompletableFuture<ArticleResponse> getNews(NewsRequestDTO dto){
        CompletableFuture<ArticleResponse> future = new CompletableFuture<>();
        System.out.println(dto);
        switch (dto.getType()){
            case EVERYTHING -> {
                newsApiClient.getEverything(
                        new EverythingRequest.Builder()
                                .q(dto.getKeyphrase())
                                .language(dto.getLanguage())
                                .sortBy(dto.getSortBy())
                                .build(),
                        getCallback(future)
                );
            }
            case TOP -> {
                newsApiClient.getTopHeadlines(
                        new TopHeadlinesRequest.Builder()
                                .q(dto.getKeyphrase())
                                .language(dto.getLanguage())
                                .category(dto.getCategory())
                                .build(),
                        getCallback(future)
                );
            }
        }

        return future;
    }

    private static NewsApiClient.ArticlesResponseCallback getCallback(CompletableFuture<ArticleResponse> future) {
        return new NewsApiClient.ArticlesResponseCallback() {
            @Override
            public void onSuccess(ArticleResponse response) {
                ArticleResponse myResponse = new ArticleResponse();
                myResponse.setArticles(response.getArticles());
                myResponse.setStatus(response.getStatus());
                myResponse.setTotalResults(response.getTotalResults());
                System.out.println(response.getTotalResults());
                future.complete(myResponse);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(throwable.getMessage());
                future.completeExceptionally(throwable);
            }
        };
    }


}
