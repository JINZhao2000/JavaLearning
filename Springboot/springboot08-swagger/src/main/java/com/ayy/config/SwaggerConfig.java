package com.ayy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 02/04/2021
 * @ Version 1.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(myInfo())
                .groupName("AYY")
                // .enable(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ayy.controller").or(RequestHandlerSelectors.basePackage("com.ayy.bean")))
                // .apis(RequestHandlerSelectors.any())
                // .apis(RequestHandlerSelectors.any())
                // .apis(RequestHandlerSelectors.withClassAnnotation(Controller.class))
                // .apis(RequestHandlerSelectors.withMethodAnnotation(RequestMapping.class))
                // .paths(PathSelectors.ant("/hello/**"))
                 .build();
    }

//    @Bean
//    public Docket docket(Environment environment){
//        Profiles profiles = Profiles.of("dev","test");
//        boolean env = environment.acceptsProfiles(profiles);
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(myInfo())
//                .enable(env);
//    }

    private ApiInfo myInfo(){
        return new ApiInfo(
                "Swagger API",
                "Swagger API Documentation Test",
                "1.0",
                "http://zhaojin.cyou/",
                new Contact(
                        "Zhao JIN",
                        "http://zhaojin.cyou/",
                        "jinzhaofr@outlook.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
