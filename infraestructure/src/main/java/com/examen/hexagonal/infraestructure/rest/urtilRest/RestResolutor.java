package com.examen.hexagonal.infraestructure.rest.urtilRest;

import com.examen.hexagonal.domain.aggregates.constants.Constants;
import com.examen.hexagonal.domain.aggregates.dto.EmpresaDTO;
import com.examen.hexagonal.domain.aggregates.response.SunatResponse;
import com.examen.hexagonal.domain.entity.EmpresaEntity;
import com.examen.hexagonal.infraestructure.exceptions.EmpresaException;
import com.examen.hexagonal.infraestructure.rest.openfeign.OpenFeignClient;
import com.examen.hexagonal.infraestructure.rest.retrofit.RetrofitClient;
import com.examen.hexagonal.infraestructure.rest.retrofit.RetrofitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Component
public class RestResolutor {

    private  OpenFeignClient openFeignClient;
    private  ModelMapper empresaMapper;
    private  RestTemplate restTemplate;
    private  RetrofitService retrofit;

    @Value("${token.api}")
    private String token;

    public RestResolutor(OpenFeignClient openFeignClient,
                         ModelMapper empresaMapper,
                         RestTemplate restTemplate
    ){
        this.openFeignClient = openFeignClient;
        this.empresaMapper = empresaMapper;
        this.restTemplate = restTemplate;
        this.retrofit = RetrofitClient.getRetrofit().create(RetrofitService.class);
    }

    public EmpresaEntity getEntityForSave(String ruc, String client){

        SunatResponse sunatResponse = executeSunat(ruc, client);

        if (sunatResponse ==  null ||
                sunatResponse.getNumeroDocumento() == null){
            throw new RuntimeException("Respuesta invalida de RENIEC: "+ ruc);
        }

        EmpresaEntity empresa = mapSunatToEmpresaEntity(sunatResponse);
        empresa.setStatus(1);
        empresa.setUserCreate("FDELAFLOR");
        empresa.setDateCreate(new Timestamp(System.currentTimeMillis()));
        return empresa;
    }

    private SunatResponse executeSunat(String ruc, String client){

        String header = "Bearer "+ token;

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
