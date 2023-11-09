package com.example.news_agregator.Entities;

import com.example.news_agregator.Services.UserService;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ReviewPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties({"username","userImg"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

}
