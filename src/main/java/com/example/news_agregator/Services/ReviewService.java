package com.example.news_agregator.Services;

import com.example.news_agregator.Entities.Review;
import com.example.news_agregator.Entities.ReviewPK;
import com.example.news_agregator.Repos.ReviewRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;

    public void save(Review review){
        reviewRepo.save(review);
    }

    public void delete(ReviewPK reviewPK){
        reviewRepo.deleteById(reviewPK);
    }

    public void deleteComment(ReviewPK pk){
        Review review = reviewRepo.findById(pk).orElseThrow();
        if (review.getIsLiked()){
            review.setCommentCreatedAt(null);
            review.setComment(null);
            reviewRepo.save(review);
        }
        else {
            reviewRepo.deleteById(pk);
        }
    }

    public boolean like(ReviewPK reviewPK){
        boolean f = true;
        try {
            Review review = reviewRepo.findById(reviewPK).orElseThrow();
            if (!review.getIsLiked()){
                review.setIsLiked(f);
                reviewRepo.save(review);
            }
            else {
                f = false;
                if (review.getComment() == null){
                    reviewRepo.deleteById(reviewPK);
                }
                else {
                    review.setIsLiked(f);
                    reviewRepo.save(review);
                }
            }
        }
        catch (Exception e){
            Review review = new Review();
            review.setReviewPK(reviewPK);
            review.setIsLiked(true);
            f = true;
            reviewRepo.save(review);
        }
        return f;
    }

    public boolean makeComment(ReviewPK reviewPK, String comment) {
        boolean f;
        try {
            Review review = reviewRepo.findById(reviewPK).orElseThrow();
            String back = review.getComment();
            review.setComment(comment);
            review.setCommentCreatedAt(LocalDateTime.now());
            reviewRepo.save(review);
            f = back == null;
        }
        catch (Exception e){
            Review review = new Review();
            review.setReviewPK(reviewPK);
            review.setIsLiked(false);
            review.setComment(comment);
            review.setCommentCreatedAt(LocalDateTime.now());
            reviewRepo.save(review);
            f = true;
        }
        System.out.println(f);
        return f;
    }
}
