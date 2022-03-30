package com.cognizant.pensiondisbursement;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.cognizant.pensiondisbursement.exchangeserverproxy.PensionerDetailsProxy;
import com.cognizant.pensiondisbursement.model.BankDetail;
import com.cognizant.pensiondisbursement.model.PensionerDetail;
import com.cognizant.pensiondisbursement.model.ProcessPensionInput;
import com.cognizant.pensiondisbursement.service.PensionDisbursementServiceImpl;

@SpringBootTest
public class PensionDisbursementServiceTests {

	@InjectMocks
	PensionDisbursementServiceImpl pensionDisbursementServiceMock;

	@Mock
	PensionerDetailsProxy pensionerDetailsProxyMock;

	@Mock
	Environment environment;

	private ProcessPensionInput processPensionInput;
	private PensionerDetail pensionerDetail;
	private BankDetail bankDetail;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		processPensionInput = new ProcessPensionInput(Long.valueOf("123456789456"), (double) 43450.0, 0.0);
		bankDetail = new BankDetail("HDFC", 5000185, "private");
		pensionerDetail = new PensionerDetail(Long.valueOf("123456789456"), "prajesh", new Date("01/02/2018"),
				"BHMAA1234A", 50000.0, 4000, "self", bankDetail);

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
	public void test1ForCorrectDetail() {
		when(pensionerDetailsProxyMock.getPensionerDetailByAadhar("123456789456")).thenReturn(pensionerDetail);
		Assertions.assertEquals(10, pensionDisbursementServiceMock.verifyData(processPensionInput));
	}

	@Test
	public void test2ForCorrectDetailWhenBankTypePublicAndPensionAmountCorrect() {
		pensionerDetail.getBankDetail().setBankType("public");
		processPensionInput.setPensionAmount(43500.0);
		when(pensionerDetailsProxyMock.getPensionerDetailByAadhar("123456789456")).thenReturn(pensionerDetail);
		Assertions.assertEquals(10, pensionDisbursementServiceMock.verifyData(processPensionInput));
	}

	@Test
	public void test3ForCorrectDetailWhenPensionTypeFamilyAndPensionAmountCorrect() {
		pensionerDetail.setPensionType("family");
		processPensionInput.setPensionAmount(28450.0);
		when(pensionerDetailsProxyMock.getPensionerDetailByAadhar("123456789456")).thenReturn(pensionerDetail);
		Assertions.assertEquals(10, pensionDisbursementServiceMock.verifyData(processPensionInput));
	}

	@Test
	public void test4PublicBankType() {
		pensionerDetail.getBankDetail().setBankType("public");
		when(pensionerDetailsProxyMock.getPensionerDetailByAadhar("123456789456")).thenReturn(pensionerDetail);
		Assertions.assertEquals(21, pensionDisbursementServiceMock.verifyData(processPensionInput));
	}

	@Test
	public void test5WrongCalculatedPensionForPensionTypeSelf() {
		processPensionInput.setPensionAmount(29000.0);
		when(pensionerDetailsProxyMock.getPensionerDetailByAadhar("123456789456")).thenReturn(pensionerDetail);
		Assertions.assertEquals(21, pensionDisbursementServiceMock.verifyData(processPensionInput));
	}

	@Test
	public void test6WrongCalculatedPensionForPensionTypeFamily() {
		pensionerDetail.setPensionType("family");
		when(pensionerDetailsProxyMock.getPensionerDetailByAadhar("123456789456")).thenReturn(pensionerDetail);
		Assertions.assertEquals(21, pensionDisbursementServiceMock.verifyData(processPensionInput));
	}

	@Test
	public void test7ForInvalidPensionType() {
		pensionerDetail.setPensionType("both");
		when(pensionerDetailsProxyMock.getPensionerDetailByAadhar("123456789456")).thenReturn(pensionerDetail);
		Assertions.assertNotEquals(10, pensionDisbursementServiceMock.verifyData(processPensionInput));
	}

	@Test
	public void test8ForWrongBankType() {
		bankDetail.setBankType("both");
		pensionerDetail.setBankDetail(bankDetail);
		when(pensionerDetailsProxyMock.getPensionerDetailByAadhar("123456789456")).thenReturn(pensionerDetail);
		Assertions.assertNotEquals(10, pensionDisbursementServiceMock.verifyData(processPensionInput));
	}

	@Test
	public void test9BankChargeForPublicBank() {
		Assertions.assertEquals(500.0,pensionDisbursementServiceMock.getBankCharge("public"));
	}
	
	@Test
	public void test10BankChargeForPrivateBank() {
		Assertions.assertEquals(550.0,pensionDisbursementServiceMock.getBankCharge("private"));
	}
}
