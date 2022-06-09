package com.selex.motor.comon.annotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExtensionFilesValidator implements ConstraintValidator<ExtensionFile, List<MultipartFile>> {

    List<String> acceptedValues;


    @Override
    public void initialize(ExtensionFile extensionFile) {
        acceptedValues = Arrays.stream(extensionFile.acceptedValue()).map(String::new).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        List<String> extensionFiles = multipartFiles.stream().map(MultipartFile::getContentType).collect(Collectors.toList());
        return extensionFiles.stream().noneMatch(k -> acceptedValues.stream().anyMatch(j -> !j.equalsIgnoreCase(k)));
    }
}
