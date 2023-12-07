package com.example.news_agregator.Services;

import com.example.news_agregator.DTOs.CommentDTO;
import com.example.news_agregator.Entities.Post;
import com.example.news_agregator.Entities.ReviewPK;
import com.example.news_agregator.Entities.User;
import com.example.news_agregator.Repos.PostRepo;
import com.example.news_agregator.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final ReviewService reviewService;
    private final String FOLDER_PATH = "D:/Desktop/универ/3 курс/рсчир/news_agregator/usersFiles/";
    public void addPost(String content, MultipartFile file, Authentication authentication) throws IOException {
        User user = userRepo.findByUsername(authentication.getName()).orElseThrow();
        Post post = new Post();
        post.setDescription(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setLikes(0);
        post.setComments(0);
        if (file != null){
            String filename = user.getUsername()+user.getPosts().size()+ Objects.requireNonNull(file.getOriginalFilename()).replace(' ','_');
            post.setPostImg(filename);
            file.transferTo(new File(FOLDER_PATH+filename));
        }
        post.setUser(user);
        postRepo.save(post);
    }

    public List<Post> getAll(){
        return postRepo.findAll();
    }

    public List<Post> getAllCurrent(Authentication authentication){
        return postRepo.findAllByUserId(userRepo.findByUsername(authentication.getName()).orElseThrow().getId());
    }

    public List<Post> getAllUsername(String username){
        return postRepo.findAllByUserId(userRepo.findByUsername(username).orElseThrow().getId());
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
        String filename = postRepo.findById(id).orElseThrow().getPostImg();
        if (filename != null){
            File file = new File(FOLDER_PATH+filename);
            file.delete();
        }
        postRepo.deleteById(id);
    }

    public List<Post> getAllSearch(String search) {
        ArrayList<Post> posts = (ArrayList<Post>) postRepo.findAll();
        posts.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        if (search.isEmpty()){
            return posts;
        }
        return posts.stream().filter(post -> post.getDescription().contains(search)).toList();
    }

    public List<Post> getAllFollowing(Authentication authentication) {
        User user = userRepo.findByUsername(authentication.getName()).orElseThrow();
        List<Post> posts = new ArrayList<>();
        for (User following : user.getFollowing()){
            posts.addAll(following.getPosts());
        }
        posts.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        return posts;
    }
}
