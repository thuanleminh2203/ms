package com.selex.motor.comon.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringEnumerationValidator implements ConstraintValidator<StringEnumeration, String> {
    private List<String> acceptedValues;

    @Override
    public void initialize(StringEnumeration annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        return acceptedValues.contains(s);
    }

}
