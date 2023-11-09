package com.example.news_agregator.Controllers;

import com.example.news_agregator.DTOs.CommentDTO;
import com.example.news_agregator.DTOs.PostDTO;
import com.example.news_agregator.Services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/add")
    public ResponseEntity<?> addPost(@RequestPart("content") String content,
                                     @RequestPart(value = "file",required = false) MultipartFile file,
                                     Authentication authentication) throws IOException {
        postService.addPost(content, file, authentication);
        return ResponseEntity.ok("post created");
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(postService.getAll());
    }
    @GetMapping
    public ResponseEntity<?> getAllCurrent(Authentication authentication){
        return ResponseEntity.ok(postService.getAllCurrent(authentication));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllUsername(@PathVariable String username){
        return ResponseEntity.ok(postService.getAllUsername(username));
    }

    @GetMapping("/{id}/review/like")
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
