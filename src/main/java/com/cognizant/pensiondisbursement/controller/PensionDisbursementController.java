package com.cognizant.pensiondisbursement.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.pensiondisbursement.model.ProcessPensionInput;
import com.cognizant.pensiondisbursement.model.ProcessPensionResponse;
import com.cognizant.pensiondisbursement.service.PensionDisbursementService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;


/**
 *  
 * @author Neelima, Ramya, Aniketh, Satya
 * 
 * PensionerDisbursementController is controller class to check for url mappings 
 * Annotated with @RestController for creating Restful controller.
 */

@RestController
@Slf4j
@PropertySource("classpath:message.properties")
public class PensionDisbursementController {

	/**
	 * Autowired Environment for fetching properties form properties file
	 */
	@Autowired
	private Environment environment;

	/**
	 * Autowired to PensionDisbursementService Interface
	 */
	@Autowired
	private PensionDisbursementService pensionService;

	/**
	 *  value for SUCCESS STATUS CODE
	 */
	private static final int SUCCESS = 10;
	
	/**
	 * pensionDisbursement function call to verify data and disburse Pension
	 * It returns response code 
	 * 10- for success
	 * 
	 * @param processPesnionInput
	 * @return int responseCode
	 */
	
	
	@PostMapping(value = "/disbursepension", produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "PensionDisbursement_fallback")
	public ResponseEntity<ProcessPensionResponse> pensionDisbursement(
			@RequestBody final ProcessPensionInput processPensionInput) {
		log.info("START :: Method :: pensionDisbursement() :: ");
		log.debug(processPensionInput.getAadharNumber() + " " + processPensionInput.getPensionAmount());
		System.out.println("hiii");
		ProcessPensionResponse response = new ProcessPensionResponse(21);

		int statusCode = pensionService.verifyData(processPensionInput);
		if (statusCode == SUCCESS) {
			log.debug(environment.getProperty("message.status") + environment.getProperty("message.10"));
			response.setPensionStatusCode(statusCode);
			log.debug("set done: " + response.getPensionStatusCode());
		} else {
			log.debug(environment.getProperty("message.status") + environment.getProperty("message.21"));
		}
		log.info("END :: Method :: pensionDisbursement() :: ");
		return new ResponseEntity<ProcessPensionResponse>(response, HttpStatus.OK);
	}

	
	public ResponseEntity<ProcessPensionResponse> PensionDisbursement_fallback(
			@RequestBody final ProcessPensionInput processInput) {
		log.debug(environment.getProperty("message.serviceDown") + new Date());
		final ProcessPensionResponse response = new ProcessPensionResponse(25);
		log.debug(environment.getProperty("message.status") + environment.getProperty("message.25"));
		return new ResponseEntity<ProcessPensionResponse>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	
	/**
	 * Return service charge of bank when bankType is passed as parameter
	 * @param bankType
	 * @return double 
	 */
	
	
	//public,private
	@PostMapping("/bankservicecharge")
	public Double bankServiceCharge(@RequestBody String bankType){
		log.info("START :: Method :: bankServiceCharge() :: ");
		log.debug(bankType);
		double serviceCharge=pensionService.getBankCharge(bankType);
		log.info("END :: Method :: bankServiceCharge() :: ");
		return serviceCharge;
			
		}
	}

