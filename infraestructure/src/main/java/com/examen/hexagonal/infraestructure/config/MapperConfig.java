package com.examen.hexagonal.infraestructure.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean(name = "defaultMapper")
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }

    /*
    @Bean(name = "reniecMapper")
    public ModelMapper reniecMapper(){
        ModelMapper mapper = new ModelMapper();

                mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.createTypeMap(ResponseReniec.class, PersonEntity.class)
                .addMapping(ResponseReniec::getNombres, (dest, v) -> dest.setFirstName((String) v))
                .addMapping(ResponseReniec::getApellidoPaterno, (dest, v) -> dest.setLastName((String) v))
                .addMapping(ResponseReniec::getApellidoMaterno, (dest, v) -> dest.setMotherLastName((String) v))
                .addMapping(ResponseReniec::getTipoDocumento, (dest, v) -> dest.setTypeDoc((String) v))
                .addMapping(ResponseReniec::getNumeroDocumento, (dest, v) -> dest.setNumDoc((String) v));
        return mapper;
    }
    */
}
