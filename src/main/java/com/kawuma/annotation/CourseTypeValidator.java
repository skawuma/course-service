package com.kawuma.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class CourseTypeValidator implements ConstraintValidator<CourseTypeValidation,String> {
    @Override
    public boolean isValid(String courseType, ConstraintValidatorContext context) {
        List list = Arrays.asList("LIVE","RECORDING", "live", "recorded");
        return list.contains(courseType);
    }
}
