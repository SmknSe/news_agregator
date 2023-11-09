package com.example.news_agregator.Repos;

import com.example.news_agregator.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    public List<Post> findAllByUserId(Long id);
}
