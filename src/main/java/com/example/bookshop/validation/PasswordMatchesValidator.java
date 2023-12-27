package com.example.bookshop.validation;

import com.example.bookshop.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordMatchesValidator implements
        ConstraintValidator<PasswordFieldsMatch, UserRegistrationRequestDto> {
    @Override
    public void initialize(PasswordFieldsMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(
            UserRegistrationRequestDto userDto,
            ConstraintValidatorContext constraintValidatorContext) {
        return Objects.equals(userDto.getPassword(), userDto.getRepeatPassword());
    }
}
