package com.project.fitness_monolith.Service;

import java.util.List;
import com.project.fitness_monolith.dto.RegisterRequest;
import com.project.fitness_monolith.dto.UserResponse;
import com.project.fitness_monolith.model.User;
import com.project.fitness_monolith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest request){
        User user=User.builder()
                .email(request.getEmail())
                        .firstName((request.getFirstName()))
                                .lastName((request.getLastName()))
                .password(request.getPassword())
                .build();
//        User user=new User(
//                null,
//                request.getEmail(),
//                request.getPassword(),
//                request.getFirstName(),
//                request.getLastName(),
//                Instant.parse("2026-08-14T13:57:37.439Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
//                Instant.parse("2026-08-14T13:57:37.439Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
//                List.of(),
//                List.of()
//
//        );

        User savedUser=userRepository.save(user);
        return mapToResponse(savedUser);

    }
    private UserResponse mapToResponse(User savedUser ) {
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;
    }

}
