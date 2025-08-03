package com.project.vehicleregistry.anotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BrandValidatorOnPatch implements ConstraintValidator<ValidBrandOnPatch, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidBrandOnPatch constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) {
            return true;
        }
        return ValidBrandEnum.execute(s);
    }
}