package com.project.vehicleregistry.anotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BrandValidator implements ConstraintValidator<ValidBrand, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidBrand constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return ValidBrandEnum.execute(s);
    }
}