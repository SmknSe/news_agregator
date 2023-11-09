package com.example.news_agregator.Controllers;

import com.example.news_agregator.Services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @GetMapping("/{path}")
    public ResponseEntity<?> addImg(@PathVariable String path){
        try {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageService.downloadImg(path));
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
