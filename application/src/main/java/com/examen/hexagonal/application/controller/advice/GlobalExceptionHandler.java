package com.examen.hexagonal.application.controller.advice;

import com.examen.hexagonal.infraestructure.exceptions.EmpresaException;
import com.examen.hexagonal.domain.aggregates.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handleConsultaReniecException(
            Throwable ex){
        ApiErrorResponse apiErrorResponse =
                new ApiErrorResponse(ex.getClass().getSimpleName(), ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(ex instanceof EmpresaException){
            status = HttpStatus.BAD_GATEWAY;
        }
        return ResponseEntity.status(status).body(apiErrorResponse);
    }
}
