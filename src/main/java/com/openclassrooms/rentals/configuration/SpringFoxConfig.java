package com.openclassrooms.rentals.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
public class SpringFoxConfig {

    @Bean
    Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()))
				.useDefaultResponseMessages(false);
				
	}

   


	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API de location")
				.description("API pour la gestion de l'ocation avec Openclassroom")
				.contact(new Contact("Ibra DIENG", "https://openclassrooms.com/fr/paths/533/projects/1303/assignment", "diengdev@gmail.com"))
				.termsOfServiceUrl("localhost")
				.version("1.0")
				.build();
		
		
	}

    
    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }
}