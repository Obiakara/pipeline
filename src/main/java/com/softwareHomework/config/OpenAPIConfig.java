package com.softwareHomework.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class OpenAPIConfig {
	
	
	
	 
    @Bean
    public OpenAPI customOpenAPI() {
    	
    	Server serverHttp = new Server();
    	Server serverHttps = new Server();
    	serverHttp.setUrl("http://cs47832.fulgentcorp.com:12166");
    	serverHttp.description("Call Swagger UI using HTTP method only");
    	serverHttps.setUrl("https://cs47832.fulgentcorp.com:12165");
    	serverHttps.description("Call swagger UI using Https");
    	
    	List<Server> listServers = new ArrayList<Server>();
    	
    	listServers.add(serverHttp);
    	listServers.add(serverHttps);
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Contact Application API").description(
                        "This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")).servers(listServers);
    }

}
