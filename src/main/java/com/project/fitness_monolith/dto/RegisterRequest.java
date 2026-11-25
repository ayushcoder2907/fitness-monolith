package com.project.fitness_monolith.dto;

import com.project.fitness_monolith.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message ="Password Required")
    private String password;
    private  String firstName;
    private String lastName;
    private UserRole role;
}
