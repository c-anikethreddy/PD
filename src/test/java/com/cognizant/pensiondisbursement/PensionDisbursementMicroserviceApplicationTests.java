package com.cognizant.pensiondisbursement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.pensiondisbursement.model.BankDetail;
import com.cognizant.pensiondisbursement.model.PensionerDetail;
import com.cognizant.pensiondisbursement.model.ProcessPensionInput;
import com.cognizant.pensiondisbursement.model.ProcessPensionResponse;

import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
class PensionDisbursementMicroserviceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testProcessInputEquals() {
		EqualsVerifier.simple().forClass(ProcessPensionInput.class).verify();
	}
	@Test
	public void testProcessPensionResponseEquals() {
		EqualsVerifier.simple().forClass(ProcessPensionResponse.class).verify();
	}


	@Test
	public void testBankDetailsEquals() {
		EqualsVerifier.simple().forClass(BankDetail.class).verify();
	}

	@Test
	public void testPensionerDetailsEquals() {
		EqualsVerifier.simple().forClass(PensionerDetail.class).verify();
	}
	
	@Test
	public void testMain() {
		PensionDisbursementMicroserviceApplication.main(new String[] {});
	}


}
