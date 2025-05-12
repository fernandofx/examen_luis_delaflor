package com.examen.hexagonal.application.usecase;

import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.domain.ports.in.EmpresaServiceIn;
import com.examen.hexagonal.domain.ports.out.EmpresaServiceOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class EmpresaServiceImpl implements EmpresaServiceIn {

    private final EmpresaServiceOut empresaServiceOut;

    @Override
    public EmpresaDTO createOpenFeignIn(String ruc) {
        String nameMethod = "createOpenFeignIn";
        String SERVICE_NAME = "EmpresaServiceImpl";
        log.info("{} - {} - INICIO", SERVICE_NAME,nameMethod);
        EmpresaDTO empresaDTO = empresaServiceOut.createOpenFeignOut(ruc);
        log.info("{} - {} - FIN", SERVICE_NAME,nameMethod);
        return empresaDTO;
    }

    @Override
    public EmpresaDTO createRetrofitIn(String ruc) {
        String nameMethod = "createRetrofitIn";
        String SERVICE_NAME = "EmpresaServiceImpl";
        log.info("{} - {} - INICIO", SERVICE_NAME,nameMethod);
        EmpresaDTO empresaDTO = empresaServiceOut.createRetrofitOut(ruc);
        log.info("{} - {} - FIN", SERVICE_NAME,nameMethod);
        return empresaDTO;
    }

    @Override
    public EmpresaDTO createRestTemplateIn(String ruc) {
        String nameMethod = "createRestTemplateIn";
        String SERVICE_NAME = "EmpresaServiceImpl";
        log.info("{} - {} - INICIO", SERVICE_NAME,nameMethod);
        EmpresaDTO empresaDTO = empresaServiceOut.createRestTemplateOut(ruc);
        log.info("{} - {} - FIN", SERVICE_NAME,nameMethod);
        return empresaDTO;
    }


}
