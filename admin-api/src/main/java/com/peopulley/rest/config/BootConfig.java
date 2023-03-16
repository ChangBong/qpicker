package com.peopulley.rest.config;

import com.peopulley.rest.common.mapper.ConvertObject;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class BootConfig {

    @PersistenceContext
    private EntityManager entityManager;

    /*
     * Entity To Dto Bean 등록
     */
    @Bean
    public ConvertObject convertObject() {
        return new ConvertObject();
    }


    /*
     * ModelMapper Bean 등록
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }



}
