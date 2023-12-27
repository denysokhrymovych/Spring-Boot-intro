package com.example.bookshop.dto.user;

import com.example.bookshop.validation.PasswordFieldsMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Data
@PasswordFieldsMatch
public class UserRegistrationRequestDto {
    @NotBlank(message = "Email cannot be null or blank")
    @Email
    private String email;

    @NotBlank(message = "Password cannot be null or blank")
    @Size(max = 20, message = "Password cannot be greater than 20 characters")
    private String password;

    @NotBlank(message = "Repeated password cannot be null or blank")
    @Size(max = 20, message = "Repeated password cannot be greater than 20 characters")
    private String repeatPassword;

    @NotBlank(message = "First name cannot be null or blank")
    @Size(max = 255, message = "First name cannot be greater than 255 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 255, message = "Last name cannot be greater than 255 characters")
    private String lastName;

    private String shippingAddress;
}
