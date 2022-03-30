package com.cognizant.pensiondisbursement;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cognizant.pensiondisbursement.controller.PensionDisbursementController;
import com.cognizant.pensiondisbursement.model.ProcessPensionInput;
import com.cognizant.pensiondisbursement.model.ProcessPensionResponse;
import com.cognizant.pensiondisbursement.service.PensionDisbursementServiceImpl;

import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
@AutoConfigureMockMvc
public class PensionDisbursementControllerTest {

	@InjectMocks
	PensionDisbursementController pensionDisbursementControllerMock;

	@Mock
	PensionDisbursementServiceImpl pensionDisbursementServiceMock;

	@Mock
	Environment environment;

	private ProcessPensionInput processPensionInput;
	private ProcessPensionResponse processPensionResponse;
	
	@Autowired
	MockMvc mockmvc;

	@BeforeEach
	public void init() {
		processPensionInput = new ProcessPensionInput(Long.valueOf("123456789456"), (double) 43450.0, 0.0);
		processPensionResponse = new ProcessPensionResponse(10);

		when(environment.getProperty("message.10")).thenReturn("Success");
		when(environment.getProperty("message.21"))
				.thenReturn("Pension amount calculated is wrong, Please redo the calculation");
		when(environment.getProperty("message.25")).thenReturn("Pensioner Detail micro service down, Please try again later");
		when(environment.getProperty("message.status")).thenReturn("returned status: ");
		when(environment.getProperty("message.serviceDown"))
				.thenReturn("Pensioner-Detail MS is down!! Fallback route enabled. Time:");
		when(environment.getProperty("message.aadhar")).thenReturn("Aadhar no: ");
		when(environment.getProperty("message.serviceCharge")).thenReturn("service charge: ");
		when(environment.getProperty("message.pension")).thenReturn("pension type: ");
		when(environment.getProperty("message.pensionCalculationSuccess")).thenReturn(" pension amount calculation success");
		when(environment.getProperty("bankType.type1")).thenReturn("public");
		when(environment.getProperty("bankType.type2")).thenReturn("private");
		when(environment.getProperty("pension.type1")).thenReturn("self");
		when(environment.getProperty("pension.type2")).thenReturn("family");

	}

	@Test
	public void testCorrectResponse() throws Exception {
		when(pensionDisbursementServiceMock.verifyData(processPensionInput)).thenReturn(10);
		mockmvc.perform(post("/pdis/disbursepension")).andReturn();
		Assertions.assertEquals(new ResponseEntity<ProcessPensionResponse>(processPensionResponse, HttpStatus.OK),
				pensionDisbursementControllerMock.pensionDisbursement(processPensionInput));
	}

	@Test
	public void testWrongResponse() throws Exception {
		processPensionResponse.setPensionStatusCode(21);
		when(pensionDisbursementServiceMock.verifyData(processPensionInput)).thenReturn(21);
		mockmvc.perform(post("/pdis/disbursepension")).andReturn();
		Assertions.assertEquals(new ResponseEntity<ProcessPensionResponse>(processPensionResponse, HttpStatus.OK),
				pensionDisbursementControllerMock.pensionDisbursement(processPensionInput));
	}

	@Test
	public void testProcessPensionResponseEquals() {
		EqualsVerifier.simple().forClass(ProcessPensionResponse.class).verify();
	}
}
