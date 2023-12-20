package com.example.bookshop.validation;

import com.example.bookshop.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<FieldMatch, Object> {
    @Override
    public void initialize(FieldMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserRegistrationRequestDto userDto = (UserRegistrationRequestDto) obj;
        return userDto.getPassword().equals(userDto.getRepeatPassword());
    }
}
