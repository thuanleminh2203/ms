package com.selex.motor.comon.annotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExtensionFileValidator implements ConstraintValidator<ExtensionFile, MultipartFile> {
    List<String> acceptedValues;

    @Override
    public void initialize(ExtensionFile extensionFile) {
        acceptedValues = Arrays.stream(extensionFile.acceptedValue()).map(String::new).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {

        return file == null || acceptedValues.stream().anyMatch(k -> k.equalsIgnoreCase(file.getContentType()));
    }


}
