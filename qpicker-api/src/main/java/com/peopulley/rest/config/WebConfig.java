package com.peopulley.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class WebConfig {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * CORS 예외처리
     */
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry){
                registry.addResourceHandler("/image/**")
                        .addResourceLocations("file:///D:/home/seegeneUpload/");
            }
        };
    }

//    @Bean
//    public JPAQueryFactory jpaQueryFactory(){
//        return new JPAQueryFactory(entityManager);
//    }

}
