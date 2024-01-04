package com.example.bookshop.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

public record UserLoginRequestDto(
        @NotBlank(message = "Email cannot be null or blank")
        @Email
        String email,

        @NotBlank(message = "Password cannot be null or blank")
        @Size(max = 20, message = "Password cannot be greater than 20 characters")
        String password
) {
}
