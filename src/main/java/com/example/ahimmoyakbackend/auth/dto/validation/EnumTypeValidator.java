package com.example.ahimmoyakbackend.auth.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumTypeValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
    private ValidEnum validEnum;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.validEnum = constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        boolean result = false;
        Object[] enumValues = this.validEnum.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value == enumValue) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}