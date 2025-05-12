package com.examen.hexagonal.infraestructure.exceptions;

public class EmpresaException extends RuntimeException{

    public EmpresaException(String message){
        super(message);
    }

    public EmpresaException(String message, Throwable cause){
        super(message,cause);
    }
}
