package com.examen.hexagonal.domain.ports.out;

import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;

public interface EmpresaServiceOut {
    EmpresaDTO createOpenFeignOut(String ruc);
    EmpresaDTO createRetrofitOut(String ruc);
    EmpresaDTO createRestTemplateOut(String ruc);
    //EmpresaDTO findByDniOut(String ruc);
}
