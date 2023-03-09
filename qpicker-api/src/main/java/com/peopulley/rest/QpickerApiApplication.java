package com.peopulley.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.peopulley.core"})
@EnableJpaRepositories(basePackages = {"com.peopulley.core"})
public class QpickerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QpickerApiApplication.class, args);
    }

}
