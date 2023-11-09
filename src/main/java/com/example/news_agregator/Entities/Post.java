package com.example.news_agregator.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private Integer likes;
    private Integer comments;
    private LocalDateTime createdAt;
    private String postImg;
    @ManyToOne
    @JsonIncludeProperties({"username","userImg"})
    private User user;
    @OneToMany(mappedBy = "reviewPK.post", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public void likePost(boolean f){
        if(f) likes++;
        else likes--;
    }

    public void comment(boolean f){
        if (f) comments++;
        else comments--;
    }

}
