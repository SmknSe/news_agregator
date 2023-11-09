package com.example.news_agregator.Services;

import com.example.news_agregator.Entities.User;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImageService {
    private final String FOLDER_PATH = "D:/Desktop/универ/3 курс/рсчир/news_agregator/usersFiles/";

    public byte[] downloadImg(String path) throws IOException {
        return Files.readAllBytes(new File(FOLDER_PATH+path).toPath());
    }
}
