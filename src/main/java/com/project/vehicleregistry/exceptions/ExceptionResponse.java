package com.project.vehicleregistry.exceptions;

public record ExceptionResponse(
        String status,
        Integer statusCode,
        String error) {
}