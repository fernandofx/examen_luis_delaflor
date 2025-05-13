package com.examen.hexagonal.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEstadoRequest {

    private String numeroDocumento;
    private String estado;
}
