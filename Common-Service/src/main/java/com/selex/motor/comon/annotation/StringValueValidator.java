package com.selex.motor.comon.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringValueValidator implements ConstraintValidator<StringValue, String> {
    private List<String> acceptedValues;

    @Override
    public void initialize(StringValue constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.acceptedValues()).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || acceptedValues.stream().anyMatch(k -> k.equals(s));
    }
}
