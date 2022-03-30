package com.cognizant.pensiondisbursement.exchangeserverproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cognizant.pensiondisbursement.model.PensionerDetail;

/**
 * @author Nayan, Akshita, Akhil
 * 
 * PensionerDetailsProxy is a feign Client interface to fetch details from PensionerDetail micro service using 
 * annotation @FeignClient and passing name of microservice and its url
 *
 */
@FeignClient(name = "Pensioner-detail-microservice", url="http://localhost:9191")
//@FeignClient(name = "Pensioner-detail-microservice", url="http://pensionerdetailmicroservice-env.us-east-1.elasticbeanstalk.com")
public interface PensionerDetailsProxy {

	/**
	 * getPensionerDetailByAadhar() will communicate to pensioner-detail microservice
	 *  and get pensioner details on basis of aadhaar.
	 * Get Mapping giving 405 error for feign client.
	 * 
	 * @param aadhaar
	 * @return PensionerDetail
	 */
	@PostMapping(value = "/pds/pensionerdetailbyaadhaar", produces = MediaType.APPLICATION_JSON_VALUE)
	//@PostMapping(value = "/pensionerdetailbyaadhaar", produces = MediaType.APPLICATION_JSON_VALUE)
	PensionerDetail getPensionerDetailByAadhar(@RequestBody String aadhaar);
}
