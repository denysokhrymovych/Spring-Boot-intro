package com.example.bookshop.dto.user;

import com.example.bookshop.validation.FieldMatch;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Data
@FieldMatch
public class UserRegistrationRequestDto {
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    private String password;
    private String repeatPassword;

    @NotNull(message = "First name cannot be null")
    @Size(max = 255, message = "First name cannot be greater than 255 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(max = 255, message = "Last name cannot be greater than 255 characters")
    private String lastName;

    private String shippingAddress;
}
