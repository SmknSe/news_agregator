package com.example.news_agregator.Services;

import com.example.news_agregator.DTOs.AuthenticationResponse;
import com.example.news_agregator.DTOs.LoginRequest;
import com.example.news_agregator.DTOs.RegisterRequest;
import com.example.news_agregator.Entities.Role;
import com.example.news_agregator.Entities.User;
import com.example.news_agregator.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.email())
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        userRepo.save(user);
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse("user registered",token, user.getUsername(), user.getEmail(),user.getCreatedAt().toString(),user.getId().toString());
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );
        User user = userRepo.findByUsername(loginRequest.username()).orElseThrow();
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse("user logged in",token, user.getUsername(), user.getEmail(),user.getCreatedAt().toString(),user.getId().toString());
    }
}
