package br.com.springboot.controleorcamento.controleorcamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Controle Orçamento API")
                .select()
                .paths(regex("/v1.*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        return new ApiInfo("Controle Orçamento API", "API utilizada pelos site e aplicativo", "V1", null, new Contact("Luiz Henrique Dandolini dos Reis","https://luizdosreis.github.io/", "luiz.reis@gmail.com"), null,null);
    }
}

