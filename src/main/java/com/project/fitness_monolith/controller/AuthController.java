
package com.project.fitness_monolith.controller;

import com.project.fitness_monolith.Service.UserService;
import com.project.fitness_monolith.dto.LoginRequest;
import com.project.fitness_monolith.dto.LoginResponse;
import com.project.fitness_monolith.dto.RegisterRequest;
import com.project.fitness_monolith.dto.UserResponse;
import com.project.fitness_monolith.model.User;
import com.project.fitness_monolith.security.JwtUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("/test")
    public String test() {
        return "Auth API is working";
    }

    // Register user
    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest user) {
        return userService.register(user);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest) {

        System.out.println("========== LOGIN STARTED ==========");
        System.out.println("LOGIN EMAIL: " + loginRequest.getEmail());

        try {

            // Step 1: Authenticate user
            User user = userService.authenticate(loginRequest);

            System.out.println("USER AUTHENTICATED");

            // Step 2: Generate JWT
            String token = jwtUtils.generateToken(
                    user.getId(),
                    user.getRole().name()
            );

            System.out.println("JWT GENERATED SUCCESSFULLY");

            // Step 3: Return response
            LoginResponse response = new LoginResponse(
                    token,
                    userService.mapToResponse(user)
            );

            System.out.println("LOGIN SUCCESSFUL");
            System.out.println("========== LOGIN COMPLETED ==========");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            System.out.println("LOGIN FAILED: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(401).build();
        }
    }
}
