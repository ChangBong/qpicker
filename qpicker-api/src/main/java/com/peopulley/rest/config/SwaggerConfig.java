package com.peopulley.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * prod 실행 방지
 */
@Configuration
@Profile({"!prod"})
@EnableSwagger2
public class SwaggerConfig {

    private String title;
    private String version;
    private String description;
    private String antPattern;

    @Bean
    public Docket api_v1() {
        version = "V1.0";
        title = "New Peopulley Qpicker API_" + version;
        description = "신규 API_" + version;
        antPattern = "/clinic/" + version.toUpperCase() + "/api/**";

        return buildApiDocket(version, title, description, antPattern);
    }

    private Docket buildApiDocket(String version, String title, String description, String antPattern){
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant(antPattern))
                .build()
                .useDefaultResponseMessages(false)
                .groupName(version)
                .apiInfo(apiInfo(version, title, description))
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .globalResponseMessage(RequestMethod.PATCH, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages());
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    private ApiInfo apiInfo(String version, String title, String description){
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

    private List<ResponseMessage> responseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message("OK")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message("Page Not Found")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message("Internal Server Error")
                .build());

        return responseMessages;
    }
}
