package com.examen.hexagonal.infraestructure.adapters;

import com.examen.hexagonal.domain.aggregates.constants.Constants;
import com.examen.hexagonal.domain.exceptions.EmpresaException;
import com.examen.hexagonal.infraestructure.repository.EmpresaRepository;
import com.examen.hexagonal.infraestructure.response.SunatResponse;
import com.examen.hexagonal.infraestructure.rest.openfeign.OpenFeignClient;
import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.domain.entity.EmpresaEntity;
import com.examen.hexagonal.domain.ports.out.EmpresaServiceOut;
import com.examen.hexagonal.infraestructure.rest.retrofit.RetrofitClient;
import com.examen.hexagonal.infraestructure.rest.retrofit.RetrofitService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;


@Service
@Log4j2
public class EmpresaAdapter implements EmpresaServiceOut {

    //INYECCION DE DEPENDENCIAS
    private final OpenFeignClient openFeignClient;
    private final ModelMapper empresaMapper;
    private final EmpresaRepository empresaRepository;
    private final RestTemplate restTemplate;
    private final RetrofitService retrofit = RetrofitClient.getRetrofit().create(RetrofitService.class);

    @Value("${token.api}")
    private String token;

    //CONSTRUCTOR
    public EmpresaAdapter(OpenFeignClient openFeignClient,
                          ModelMapper empresaMapper,
                          EmpresaRepository empresaRepository,
                          RestTemplate restTemplate) {

        this.openFeignClient = openFeignClient;
        this.restTemplate = restTemplate;
        this.empresaMapper = empresaMapper;
        this.empresaRepository = empresaRepository;
    }

    //IMPLEMENTA LOS METODOS DEL PUERTO OUT
    @Override
    public EmpresaDTO createOpenFeignOut(String ruc) {
        return mapToEmpresaDTO(empresaRepository.save(getEntityForSave(ruc, "openfeign")));
    }

    @Override
    public EmpresaDTO createRetrofitOut(String ruc) {
        return mapToEmpresaDTO(empresaRepository.save(getEntityForSave(ruc, "retrofit")));
    }

    @Override
    public EmpresaDTO createRestTemplateOut(String ruc) {
        return mapToEmpresaDTO(empresaRepository.save(getEntityForSave(ruc, "rest_template")));
    }

    //======================================================================================================
    //                                               METODOS DE APOYO
    //======================================================================================================

    private EmpresaEntity getEntityForSave(String ruc, String client){

        SunatResponse sunatResponse = executeSunat(ruc, client);

        if (sunatResponse ==  null ||
                sunatResponse.getNumeroDocumento() == null){
            throw new RuntimeException("Respuesta invalida de RENIEC: "+ ruc);
        }

        EmpresaEntity empresa = mapSunatToEmpresaEntity(sunatResponse);
        empresa.setStatus(1);
        empresa.setUserCreate("PRODRIGUEZ");
        empresa.setDateCreate(new Timestamp(System.currentTimeMillis()));
        return empresa;
    }

    private SunatResponse executeSunat(String ruc, String client){

        String header = "Bearer "+token;

        switch (client) {
            case "openfeign" -> {
                return Optional.ofNullable(openFeignClient.getInfoSunat(ruc, header))
                        .orElseThrow(() -> new RuntimeException("Error al consutla con Reniec"));
            }
            case "retrofit" -> {
                try {
                    Response<SunatResponse> executeSunat = retrofit.findSunat("Bearer " + token, ruc).execute();
                    if (executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())) {
                        return executeSunat.body();
                    } else {
                        throw new RuntimeException("No existe la empresa solicitada");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "rest_template" -> {
                String urlComplete = Constants.BASE_URL + "v2/sunat/ruc?numero=" + ruc;
                try {
                    ResponseEntity<SunatResponse> response = restTemplate.exchange(
                            urlComplete,
                            HttpMethod.GET,
                            new HttpEntity<>(createHeaders()),
                            SunatResponse.class
                    );
                    if (response.getStatusCode() == HttpStatus.OK) {
                        return response.getBody();
                    } else {
                        throw new EmpresaException("Respuesta Inesperada del servicio de RENIEC. Codigo: "
                                + response.getStatusCode());
                    }
                } catch (Exception e) {
                    throw new EmpresaException("Respuesta Inesperada del servicio de RENIEC: {}" + ruc, e);
                }
            } default -> throw new EmpresaException("Cliente no identificado" );
        }
    }

    //MAPEOS
    private EmpresaDTO mapToEmpresaDTO(EmpresaEntity empresaEntity){
        return empresaMapper.map(empresaEntity,EmpresaDTO.class);
    }

    private EmpresaEntity mapSunatToEmpresaEntity(SunatResponse sunatResponse){
        return empresaMapper.map(sunatResponse, EmpresaEntity.class);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization",Constants.BEARER+token);
        return header;
    }

}
