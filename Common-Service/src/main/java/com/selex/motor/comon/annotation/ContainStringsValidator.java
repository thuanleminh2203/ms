package com.selex.motor.comon.annotation;

import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ContainStringsValidator implements ConstraintValidator<ContainStrings, List<String>> {

    List<String> acceptValues;

    @Override
    public void initialize(ContainStrings strings) {
        acceptValues = Arrays.stream(strings.acceptedValue()).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(List<String> stringList, ConstraintValidatorContext constraintValidatorContext) {
        return CollectionUtils.isEmpty(stringList) || stringList.stream().allMatch(k -> acceptValues.stream().anyMatch(j -> j.equals(k)));
    }
}
