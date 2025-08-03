package com.project.vehicleregistry.utils;

import java.time.LocalDate;

public class DecadeFinder {
    public static int find(int year) {
        var localDate = LocalDate.of(year, 1, 1);
        return (localDate.getYear() / 10) * 10;
    }
}