package com.example.news_agregator.Services;

import com.example.news_agregator.Entities.User;
import com.example.news_agregator.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    public List<User> getAll() {
        return userRepo.findAll();
    }
}
