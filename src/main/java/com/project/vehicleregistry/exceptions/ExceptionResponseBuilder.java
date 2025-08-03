package com.project.vehicleregistry.exceptions;

public class ExceptionResponseBuilder {
    private String status;
    private Integer statusCode;
    private String error;

    public ExceptionResponseBuilder(String status, int statusCode, String error) {
        this.status = status;
        this.statusCode = statusCode;
        this.error = error;
    }

    public ExceptionResponse build() {
        return new ExceptionResponse(this.status, this.statusCode, this.error);
    }
}