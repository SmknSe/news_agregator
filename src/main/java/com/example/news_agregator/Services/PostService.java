package com.example.news_agregator.Services;

import com.example.news_agregator.DTOs.CommentDTO;
import com.example.news_agregator.DTOs.PostDTO;
import com.example.news_agregator.Entities.Post;
import com.example.news_agregator.Entities.ReviewPK;
import com.example.news_agregator.Entities.User;
import com.example.news_agregator.Repos.PostRepo;
import com.example.news_agregator.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final ReviewService reviewService;
    public void addPost(PostDTO dto, Authentication authentication) {
        Post post = new Post();
        post.setDescription(dto.getDescription());
        post.setCreatedAt(dto.getCreatedAt());
        post.setLikes(dto.getLikes());
        post.setComments(dto.getComments());
        post.setUser(userRepo.findByUsername(authentication.getName()).orElseThrow());
        postRepo.save(post);
    }

    public List<Post> getAll(){
        return postRepo.findAll();
    }

    public void makeReview(Long id, CommentDTO comment, Authentication authentication) {
        Post post = postRepo.findById(id).orElseThrow();
        if (comment == null){
            boolean f = reviewService.like(new ReviewPK(userRepo.findByUsername(authentication.getName()).orElseThrow(),post));
            post.likePost(f);
        }
        else {
            boolean f = reviewService.makeComment(new ReviewPK(userRepo.findByUsername(authentication.getName()).orElseThrow(),post), comment.comment());
            if (f) post.comment(f);
        }
        postRepo.save(post);
    }

    public void deleteComment(Long id, Authentication authentication){
        Post post = postRepo.findById(id).orElseThrow();
        User user = userRepo.findByUsername(authentication.getName()).orElseThrow();
        reviewService.deleteComment(new ReviewPK(user,post));
        post.comment(false);
        postRepo.save(post);
    }

    public void delete(Long id){
        postRepo.deleteById(id);
    }
}
