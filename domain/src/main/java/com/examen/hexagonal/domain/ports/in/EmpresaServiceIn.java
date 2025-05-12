package com.examen.hexagonal.domain.ports.in;


import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;

public interface EmpresaServiceIn {
    EmpresaDTO createOpenFeignIn(String ruc);
    EmpresaDTO createRetrofitIn(String ruc);
    EmpresaDTO createRestTemplateIn(String ruc);
}
