package com.examen.hexagonal.application.controller.advice;

import com.examen.hexagonal.infraestructure.exceptions.EmpresaException;
import com.examen.hexagonal.domain.aggregates.response.ApiErrorResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handleConsultaReniecException(
            Throwable ex){

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getClass().getSimpleName(), ex.getMessage());
        HttpStatus status = HttpStatus.FORBIDDEN;
        if(ex instanceof EmpresaException){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }else if(ex instanceof NoHandlerFoundException){
            status = HttpStatus.NOT_FOUND;
            apiErrorResponse.setMessage("No se encontró la empresa solicitada");
        }else if(ex instanceof HttpClientErrorException.BadRequest){
            status = HttpStatus.BAD_REQUEST;
            apiErrorResponse.setMessage("Los datos enviados son invalidos");
        }else if(ex instanceof HttpServerErrorException.InternalServerError){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            apiErrorResponse.setMessage("Hubo un error inesperado");
        }else if(ex instanceof HttpClientErrorException.Unauthorized){
            status = HttpStatus.UNAUTHORIZED;
            apiErrorResponse.setMessage("No autorizado");
        }
        return ResponseEntity.status(status).body(apiErrorResponse);
    }
}
