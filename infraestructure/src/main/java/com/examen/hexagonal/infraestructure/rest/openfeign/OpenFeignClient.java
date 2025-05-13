package com.examen.hexagonal.infraestructure.rest.openfeign;

import com.examen.hexagonal.domain.aggregates.response.SunatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sunatCLient",url = "https://api.apis.net.pe/v2/sunat/")
public interface OpenFeignClient {

    @GetMapping("/ruc")
    SunatResponse getInfoSunat(@RequestParam("numero")String numero,
                               @RequestHeader("Authorization") String authorization);


}
