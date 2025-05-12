package com.examen.hexagonal.infraestructure.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SunatResponse {

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
}
