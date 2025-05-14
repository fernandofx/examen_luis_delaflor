package com.examen.hexagonal.domain.aggregates.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class ResponseBase <T>{

    private String codigo;
    private String mensaje;
    private Optional<T> data;
}
