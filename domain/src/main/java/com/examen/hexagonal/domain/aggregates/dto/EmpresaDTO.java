package com.examen.hexagonal.domain.aggregates.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpresaDTO {

    private Long id;
    private String razonSocial;
    private Integer tipoDocumento;
    private String numeroDocumento;
    private String estado;
    private String condicion;
    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;
    private String actividadEconomica;
    private String numeroTrabajadores;
    private Integer status;
    private String userCreate;
    private Timestamp dateCreate;
}
