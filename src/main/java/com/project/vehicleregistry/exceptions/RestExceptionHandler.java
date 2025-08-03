package com.project.vehicleregistry.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NotFoundException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Recurso não encontrado",
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Parametros invalidos",
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return exceptionResponseBuilder.build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exception(Exception e) {
        ExceptionResponseBuilder exceptionResponseBuilder = new ExceptionResponseBuilder(
                "Erro interno encontra, por favor tente novamente",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "erro interno"
        );
        return exceptionResponseBuilder.build();
    }
}