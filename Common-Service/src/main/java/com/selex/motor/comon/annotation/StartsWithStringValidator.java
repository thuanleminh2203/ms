package com.selex.motor.comon.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartsWithStringValidator implements ConstraintValidator<StartsWithString, String> {

    String acceptedValue;

    @Override
    public void initialize(StartsWithString constraintAnnotation) {
        acceptedValue = constraintAnnotation.acceptedValue();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.startsWith(acceptedValue);
    }
}
