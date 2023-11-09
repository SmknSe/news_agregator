package com.example.news_agregator.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class Review {
    @EmbeddedId
    private ReviewPK reviewPK;

    private Boolean isLiked = false;
    private String comment;
    private LocalDateTime commentCreatedAt;
}
