package com.example.news_agregator.Repos;

import com.example.news_agregator.Entities.Review;
import com.example.news_agregator.Entities.ReviewPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review,ReviewPK> {
}
