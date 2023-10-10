package com.example.news_agregator.Controllers;

import com.example.news_agregator.DTOs.CommentDTO;
import com.example.news_agregator.DTOs.PostDTO;
import com.example.news_agregator.Services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestBody PostDTO dto, Authentication authentication){
        postService.addPost(dto, authentication);
        return ResponseEntity.ok("post created");
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(postService.getAll());
    }

    @PostMapping("/{id}/review/like")
    public ResponseEntity<?> makeLike(@PathVariable Long id, Authentication authentication){
        postService.makeReview(id,null, authentication);
        return ResponseEntity.ok("liked/unliked");
    }

    @PostMapping("/{id}/review/comment")
    public ResponseEntity<?> makeComment(@PathVariable Long id, @RequestBody CommentDTO dto, Authentication authentication){
        postService.makeReview(id,dto, authentication);
        return ResponseEntity.ok("commented");
    }

    @DeleteMapping("/{id}/review/comment")
    public ResponseEntity<?> deleteReview(@PathVariable Long id, Authentication authentication){
        postService.deleteComment(id, authentication);
        return ResponseEntity.ok("comment deleted");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.ok("post deleted");
    }
}
