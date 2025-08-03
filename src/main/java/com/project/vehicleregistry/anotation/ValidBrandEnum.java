package com.project.vehicleregistry.anotation;

import com.project.vehicleregistry.enums.Brand;

public class ValidBrandEnum {
    public static boolean execute(String s) {
        try {
            Brand.valueOf(s.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}