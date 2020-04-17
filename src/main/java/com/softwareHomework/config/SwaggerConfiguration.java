//package com.softwareHomework.config;
//
//import java.util.Collections;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//
//@EnableSwagger2
//@Configuration
//public class SwaggerConfiguration {
//	
//	
//	/**
//	 * Return a prepared docket instance  which stores the Swagger configurations
//	 * @return
//	 */
//	@Bean
//	public Docket swaggerDocketConfig(){
//
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.paths(PathSelectors.ant("/properties/**"))//restrict based on path
//				.apis(RequestHandlerSelectors.basePackage("com.softwareHomework.controller"))//restrict based on package
//				.build()
//				.apiInfo(apiDetails());
//		
//	}
//	
//	/**
//	 * Creates the Information Object related to the API 
//	 * @return
//	 */
//	private ApiInfo apiDetails(){
//		
//		return new ApiInfo(
//				
//				"Advanced Software API",
//				"API for Advanced Software class",
//				"1.0",
//				"Partially free to use",
//				new springfox.documentation.service.Contact("Chinedu Obiakara", "https://utsa.edu", "chinedu.obiakara@utsa.edu"),
//				"API License",
//				"http://utsa.edu",
//				Collections.emptyList()
//				);
//		
//		
//	}
//
//}
