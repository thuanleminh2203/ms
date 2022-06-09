package com.selex.motor.comon.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class StringValidator implements ConstraintValidator<ValidateString, String> {
    private List<String> valueList;

    public StringValidator() {
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value==null) return true;
        return !StringUtils.isEmpty(value) && this.valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(ValidateString constraintAnnotation) {
        this.valueList = new ArrayList();
        String[] var2 = constraintAnnotation.acceptedValues();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String val = var2[var4];
            this.valueList.add(val.toUpperCase());
        }

    }
}