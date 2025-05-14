package com.examen.hexagonal.infraestructure.adapters;

import com.examen.hexagonal.infraestructure.exceptions.EmpresaException;
import com.examen.hexagonal.infraestructure.repository.EmpresaRepository;
import com.examen.hexagonal.domain.aggregates.request.UpdateEstadoRequest;
import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.domain.entity.EmpresaEntity;
import com.examen.hexagonal.domain.ports.out.EmpresaServiceOut;
import com.examen.hexagonal.infraestructure.rest.urtilRest.RestResolutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class EmpresaAdapter implements EmpresaServiceOut {

    //INYECCION DE DEPENDENCIAS
    private final EmpresaRepository empresaRepository;
    private final RestResolutor restResolutor;

    @Value("${token.api}")
    private String token;

    //CONSTRUCTOR
    public EmpresaAdapter(EmpresaRepository empresaRepository,
                          RestResolutor restResolutor) {
        this.empresaRepository = empresaRepository;
        this.restResolutor = restResolutor;
    }

    //IMPLEMENTA LOS METODOS DEL PUERTO OUT
    @Override
    public EmpresaDTO createOpenFeignOut(String ruc) {
        return buildEmpresaDto(empresaRepository.save(restResolutor.getEntityForSave(ruc, "openfeign")));
    }

    @Override
    public EmpresaDTO createRetrofitOut(String ruc) {
        return buildEmpresaDto(empresaRepository.save(restResolutor.getEntityForSave(ruc, "retrofit")));
    }

    @Override
    public EmpresaDTO createRestTemplateOut(String ruc) {
        return buildEmpresaDto(empresaRepository.save(restResolutor.getEntityForSave(ruc, "rest_template")));
    }

    @Override
    public EmpresaDTO findByRucOut(String ruc) {
        EmpresaEntity empresaEntity = empresaRepository.findByNumeroDocumento(ruc);
        Optional<EmpresaEntity> entity = Optional.ofNullable(empresaEntity);
        if(entity.isPresent()) {
            return buildEmpresaDto(empresaEntity);
        }else{
            throw new EmpresaException("No se ha encontrado la empresa solicitada");
        }
    }

    @Override
    public EmpresaDTO updateEstadoByRucOut(UpdateEstadoRequest updateEstadoRequest) {
        EmpresaEntity empresaEntity = empresaRepository.findByNumeroDocumento(updateEstadoRequest.getNumeroDocumento());
        Optional<EmpresaEntity> entity = Optional.ofNullable(empresaEntity);

        if(entity.isPresent()){
            empresaEntity.setEstado(updateEstadoRequest.getEstado());
            return buildEmpresaDto(empresaRepository.save(empresaEntity));
        }else{
            throw new EmpresaException("No se ha encontrado la empresa solicitada");
        }

    }

    @Override
    public EmpresaDTO deleteByRucOut(String ruc) {
        EmpresaEntity empresaEntity = empresaRepository.findByNumeroDocumento(ruc);
        Optional<EmpresaEntity> entity = Optional.ofNullable(empresaEntity);
        if(entity.isPresent()){
            empresaRepository.delete(empresaEntity);
            return buildEmpresaDto(empresaEntity);
        }else{
            throw new EmpresaException("No se ha encontrado la empresa solicitada");
        }
    }

    @Override
    public List<EmpresaDTO> findByAllOut() {
        List<EmpresaDTO> listDTO;
        List<EmpresaEntity> listEmpresaEntity = empresaRepository.findAll();
        if(!listEmpresaEntity.isEmpty()){
            listDTO = listEmpresaEntity.stream().map(this::buildEmpresaDto).toList();
            return listDTO;
        }
        throw new EmpresaException("No se encontraron registros de empresas en la DB");

    }


    //======================================================================================================
    //                                               METODOS DE APOYO
    //======================================================================================================
    private EmpresaDTO buildEmpresaDto(EmpresaEntity empresaEntity){
        return EmpresaDTO.builder()
                .id(empresaEntity.getId())
                .razonSocial(empresaEntity.getRazonSocial())
                .tipoDocumento(empresaEntity.getTipoDocumento())
                .numeroDocumento(empresaEntity.getNumeroDocumento())
                .estado(empresaEntity.getEstado())
                .condicion(empresaEntity.getCondicion())
                .direccion(empresaEntity.getDireccion())
                .departamento(empresaEntity.getDepartamento())
                .provincia(empresaEntity.getProvincia())
                .distrito(empresaEntity.getDistrito())
                .actividadEconomica(empresaEntity.getActividadEconomica())
                .numeroTrabajadores(empresaEntity.getNumeroTrabajadores())
                .status(empresaEntity.getStatus())
                .userCreate(empresaEntity.getUserCreate())
                .dateCreate(empresaEntity.getDateCreate())
                .build();
    }


}
