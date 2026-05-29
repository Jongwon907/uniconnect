package com.example.demo.common.validation.validator;

import com.example.demo.common.validation.annotation.ValidEmail;
import com.example.demo.application.signup.dto.UserAdditionalInfoDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, UserAdditionalInfoDto> {
    @Override
    public boolean isValid(UserAdditionalInfoDto dto, ConstraintValidatorContext context) {
        return dto != null && dto.getUniversity().matches(dto.getEmail());
    }
}