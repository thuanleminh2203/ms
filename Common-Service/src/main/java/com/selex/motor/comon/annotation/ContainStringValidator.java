package com.selex.motor.comon.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContainStringValidator implements ConstraintValidator<ContainString, String> {
    String acceptedValue;

    @Override
    public void initialize(ContainString constraintAnnotation) {
        acceptedValue = constraintAnnotation.acceptedValue();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return !StringUtils.hasLength(s) || s.contains(acceptedValue);
    }

}
