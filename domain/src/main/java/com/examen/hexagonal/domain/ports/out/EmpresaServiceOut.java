package com.examen.hexagonal.domain.ports.out;

import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.domain.aggregates.request.UpdateEstadoRequest;

import java.util.List;

public interface EmpresaServiceOut {
    EmpresaDTO createOpenFeignOut(String ruc);
    EmpresaDTO createRetrofitOut(String ruc);
    EmpresaDTO createRestTemplateOut(String ruc);
    EmpresaDTO findByRucOut(String ruc);
    EmpresaDTO updateEstadoByRucOut(UpdateEstadoRequest updateEstadoRequest);
    EmpresaDTO deleteByRucOut(String ruc);
    List<EmpresaDTO> findByAllOut();
}
