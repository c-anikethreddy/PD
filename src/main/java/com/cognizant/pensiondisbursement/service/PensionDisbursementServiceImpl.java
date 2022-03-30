package com.cognizant.pensiondisbursement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cognizant.pensiondisbursement.exchangeserverproxy.PensionerDetailsProxy;
import com.cognizant.pensiondisbursement.model.PensionerDetail;
import com.cognizant.pensiondisbursement.model.ProcessPensionInput;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Nayan, Akshita, Akhil
 * 
 * PensionDisbursementServiceImpl is the implementation class for PensionDisbursementService interface
 * It is service class that has the implementation for verifyData method
 */
@Service
@Slf4j
@PropertySource("classpath:message.properties")
public class PensionDisbursementServiceImpl implements PensionDisbursementService {

	/**
	 * environment reference of Environment is autowired that read properties from messages.properties file
	 */
	@Autowired
	private Environment environment;

	/**
	 * pensionerDetailsProxy reference of PensionerDetailsProxy Autowired 
	 */
	@Autowired
	private PensionerDetailsProxy pensionerDetailsProxy;
	
	
	/** public bank charges */
	private static final double PUBLIC_BANK_CHARGE = 500.0;
	
	/** public bank charges */
	private static final double PRIVATE_BANK_CHARGE = 550.0;
	
	/**
	 * verifyData() takes processPensionInput as parameter verifies it with pensioner detail and return reponse code
	 * @param processPensionInput
	 * @return int
	 */
	@Override
	public int verifyData(ProcessPensionInput processPensionInput) {
		// TODO Auto-generated method stub
		log.info("START :: Method :: verifyData() :: ");
		log.debug(environment.getProperty("message.aadhar") + processPensionInput.getAadharNumber().toString());

		final PensionerDetail pensionerDetail = pensionerDetailsProxy
				.getPensionerDetailByAadhar(Long.toString(processPensionInput.getAadharNumber()));

		final String BankType = pensionerDetail.getBankDetail().getBankType();
		if (BankType.equalsIgnoreCase(environment.getProperty("bankType.type1"))) {
			 processPensionInput.setBankServiceCharge(PUBLIC_BANK_CHARGE);
		} else if (BankType.equalsIgnoreCase(environment.getProperty("bankType.type2"))) {
			processPensionInput.setBankServiceCharge(PRIVATE_BANK_CHARGE);
		} else {
			return 21;
		}

		log.debug(environment.getProperty("message.pension") + pensionerDetail.getPensionType());
		if (pensionerDetail.getPensionType().equalsIgnoreCase(environment.getProperty("pension.type1"))) {

			if (processPensionInput.getPensionAmount() == 0.8 * pensionerDetail.getSalaryEarned()
					+ pensionerDetail.getAllowances() - processPensionInput.getBankServiceCharge()) {
				log.debug(environment.getProperty("pension.type2") + environment.getProperty("message.pensionCalculationSuccess"));
				return 10;
			}
		}

		else if (pensionerDetail.getPensionType().equalsIgnoreCase(environment.getProperty("pension.type2"))) {
			if (processPensionInput.getPensionAmount() == 0.5 * pensionerDetail.getSalaryEarned()
					+ pensionerDetail.getAllowances() - processPensionInput.getBankServiceCharge()) {
				log.debug(environment.getProperty("pension.type2") + environment.getProperty("message.pensionCalculationSuccess"));
				return 10;
			}
		}
		log.info("END :: Method :: verifyData() :: ");
		return 21;	}


	/**
	 * getBankCharge() takes bankType as parameter and return serviceCharge depending on bankType
	 * @param bankType
	 * @return double serviceCharge
	 */
	@Override
	public double getBankCharge(String bankType) {
		log.info("START :: Method :: getBankCharge() :: ");
		double bankCharge=0.0;
		if(bankType.equalsIgnoreCase(environment.getProperty("bankType.type1"))) {
			bankCharge=PUBLIC_BANK_CHARGE;
		}
		else if(bankType.equalsIgnoreCase(environment.getProperty("bankType.type2"))) {
			bankCharge=PRIVATE_BANK_CHARGE;
		}
		log.debug(environment.getProperty("message.serviceCharge")+bankCharge);
		log.info("END :: Method :: getBankCharge() :: ");
		return bankCharge;
	}

}
