package com.examen.hexagonal.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "empresas")
@Getter
@Setter
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razonSocial;
    private Integer tipoDocumento;
    @Column(unique = true)
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

