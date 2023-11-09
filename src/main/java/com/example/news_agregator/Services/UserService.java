package com.example.news_agregator.Services;

import com.example.news_agregator.Entities.User;
import com.example.news_agregator.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    public List<User> getAll() {
        return userRepo.findAll();
    }
    private final String FOLDER_PATH = "D:/Desktop/универ/3 курс/рсчир/news_agregator/usersFiles/";

    public String uploadUserImg(MultipartFile file, Authentication authentication) throws IOException {
        String filename = authentication.getName()+"Avatar"+file.getOriginalFilename().replace(' ','_');
        String filePath = FOLDER_PATH+filename;
        User user = userRepo.findByUsername(authentication.getName()).orElseThrow();
        user.setUserImg(filename);
        userRepo.save(user);
        file.transferTo(new File(filePath));
        return "file uploaded as " + file.getOriginalFilename();
    }

    public User getCurrentUser(Authentication authentication) {
        return userRepo.findByUsername(authentication.getName()).orElseThrow();
    }

    public User getUser(String username) {
        return userRepo.findByUsername(username).orElseThrow();
    }
}
