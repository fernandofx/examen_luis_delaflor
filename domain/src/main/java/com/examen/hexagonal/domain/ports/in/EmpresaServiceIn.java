package com.examen.hexagonal.domain.ports.in;


import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.domain.aggregates.request.UpdateEstadoRequest;

public interface EmpresaServiceIn {
    EmpresaDTO createOpenFeignIn(String ruc);
    EmpresaDTO createRetrofitIn(String ruc);
    EmpresaDTO createRestTemplateIn(String ruc);
    EmpresaDTO findByRucIn(String ruc);
    EmpresaDTO updateEstadoByRucIn(UpdateEstadoRequest updateEstadoRequest);
}
