package com.project.fitness_monolith.controller;

import com.project.fitness_monolith.Service.UserService;
import com.project.fitness_monolith.dto.RegisterRequest;
import com.project.fitness_monolith.dto.UserResponse;
import com.project.fitness_monolith.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;



    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest user){
        return userService.register(user);
    }

}
