package com.examen.hexagonal.infraestructure.rest.restemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RestTemplateClient {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
