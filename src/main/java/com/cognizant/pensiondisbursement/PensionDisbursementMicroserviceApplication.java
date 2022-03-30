package com.cognizant.pensiondisbursement;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author Neelima, Ramya, Aniketh, Satya
 * Main class for Pensioner Detail MicroService
 * Annotated with @SpringBootApplication, @ComponentScan to scan all base packages
 * Annotated with @EnableFeignClients for creating REST API clients
 * Annotated with @EnableCircuitBreaker to set up a fallback in application logic
 *
 */
@SpringBootApplication
@EnableFeignClients("com.cognizant.pensiondisbursement.exchangeserverproxy")
@EnableCircuitBreaker
@EnableSwagger2
public class PensionDisbursementMicroserviceApplication {


	/**
	 * Main function to run the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(PensionDisbursementMicroserviceApplication.class, args);
	}

	/**
	 * Swagger Docket
	 * @return
	 */
	@Bean
	public Docket swaggerConfiguration()
		{
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.cognizant.pensiondisbursement"))
					.build()
					.apiInfo(apiDetails());
		
		}

	/**
	 * Swagger Api Info
	 * @return
	 */
	private ApiInfo apiDetails()
	{
		
		return new ApiInfo(
				"Pension Disbursement Service",
				"Microservice From Pension Management Project",
				"1.0",
				"Free To Use",
				new springfox.documentation.service.Contact("Admin", "", "admin@cognizant.com"),
				"API Licesence",
				"....", Collections.emptyList());
		}

}
