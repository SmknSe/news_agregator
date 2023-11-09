package com.example.news_agregator.Controllers;

import com.example.news_agregator.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getCurrentUser(Authentication authentication){
        return ResponseEntity.ok(userService.getCurrentUser(authentication));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PostMapping("/addImg")
    public ResponseEntity<?> addImg(@RequestParam("image")MultipartFile file, Authentication authentication){
        try {
            return ResponseEntity.ok(userService.uploadUserImg(file, authentication));
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
