//package com.project.fitness_monolith.Service;
//
//import java.util.List;
//
//import com.project.fitness_monolith.dto.LoginRequest;
//import com.project.fitness_monolith.dto.RegisterRequest;
//import com.project.fitness_monolith.dto.UserResponse;
//import com.project.fitness_monolith.model.User;
//import com.project.fitness_monolith.model.UserRole;
//import com.project.fitness_monolith.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
////import org.hibernate.mapping.List;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.ZoneOffset;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserResponse register(RegisterRequest request){
//        UserRole role= request.getRole()!=null? request.getRole() : UserRole.USER;
//        User user=User.builder()
//                .email(request.getEmail())
//                        .firstName((request.getFirstName()))
//                                .lastName((request.getLastName()))
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(role)
//                .build();
////        User user=new User(
////                null,
////                request.getEmail(),
////                request.getPassword(),
////                request.getFirstName(),
////                request.getLastName(),
////                Instant.parse("2026-08-14T13:57:37.439Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
////                Instant.parse("2026-08-14T13:57:37.439Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
////                List.of(),
////                List.of()
////
////        );
//
//        User savedUser=userRepository.save(user);
//        return mapToResponse(savedUser);
//
//    }
//    public UserResponse mapToResponse(User savedUser) {
//        UserResponse response = new UserResponse();
//        response.setId(savedUser.getId());
//        response.setEmail(savedUser.getEmail());
//        response.setPassword(savedUser.getPassword());
//        response.setFirstName(savedUser.getFirstName());
//        response.setLastName(savedUser.getLastName());
//        response.setCreatedAt(savedUser.getCreatedAt());
//        response.setUpdatedAt(savedUser.getUpdatedAt());
//        return response;
//    }
//
//    public User findByEmail(String email) {
//        return null;
//    }
//
//    public User authenticate(LoginRequest loginRequest) {
//
//        User user=userRepository.findByEmail(loginRequest.getEmail());
//        if(user==null){
//            throw new RuntimeException("INVALID CREDENTIALS");
//        }
//        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
//            throw new RuntimeException("INVALID CREDENTIALS");
//        }
//        return user;
//    }
//}



package com.project.fitness_monolith.Service;

import java.util.List;

import com.project.fitness_monolith.dto.LoginRequest;
import com.project.fitness_monolith.dto.RegisterRequest;
import com.project.fitness_monolith.dto.UserResponse;
import com.project.fitness_monolith.model.User;
import com.project.fitness_monolith.model.UserRole;
import com.project.fitness_monolith.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Register a new user
//    public UserResponse register(RegisterRequest request) {
//
//        UserRole role = request.getRole() != null
//                ? request.getRole()
//                : UserRole.USER;
//
//        User user = User.builder()
//                .email(request.getEmail())
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(role)
//                .build();
//
//        User savedUser = userRepository.save(user);
//
//        return mapToResponse(savedUser);
//    }

    public UserResponse register(RegisterRequest request) {

        UserRole role = request.getRole() != null
                ? request.getRole()
                : UserRole.USER;

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        System.out.println("========== REGISTER DEBUG ==========");
        System.out.println("EMAIL: " + request.getEmail());
        System.out.println("RAW PASSWORD LENGTH: " + request.getPassword().length());
        System.out.println("HASH LENGTH: " + encodedPassword.length());

        boolean matches = passwordEncoder.matches(
                request.getPassword(), encodedPassword
        );

        System.out.println("IMMEDIATE BCrypt MATCH: " + matches);

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(encodedPassword)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        System.out.println("USER SAVED SUCCESSFULLY");

        return mapToResponse(savedUser);
    }

    // Convert User entity into UserResponse DTO
    public UserResponse mapToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }

    // Find user using email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Authenticate user
//    public User authenticate(LoginRequest loginRequest) {
//
//        User user = findByEmail(loginRequest.getEmail());
//
//        if (user == null) {
//            throw new RuntimeException("INVALID CREDENTIALS");
//        }
//
//        if (!passwordEncoder.matches(
//                loginRequest.getPassword(),
//                user.getPassword())) {
//            throw new RuntimeException("INVALID CREDENTIALS");
//        }
//
//        return user;
//    }
    public User authenticate(LoginRequest loginRequest) {

        System.out.println("LOGIN METHOD CALLED");

        User user = userRepository.findByEmail(loginRequest.getEmail());

        System.out.println("USER FOUND: " + (user != null));

        if (user == null) {
            throw new RuntimeException("USER NOT FOUND");
        }

        System.out.println("LOGIN PASSWORD LENGTH: "
                + loginRequest.getPassword().length());

        System.out.println("DATABASE HASH LENGTH: "
                + user.getPassword().length());

        System.out.println("PASSWORD MATCHED: "
                + passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        ));

        if (!passwordEncoder.matches(
                loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("PASSWORD MISMATCH");
        }

        return user;
    }
}
