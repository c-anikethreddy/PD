package com.cognizant.pensiondisbursement.service;

import com.cognizant.pensiondisbursement.model.ProcessPensionInput;

/**
 * @author Nayan, Akshita, Akhil
 * PensionDisbursementService Interface for PensionDisbursement microservice
 *
 */
public interface PensionDisbursementService {

	/**  
	 * Function to verify Data that if pension calculated is correct or not
	 */
	public int verifyData(ProcessPensionInput processPensionInput);
	
	/**  
	 * Function to get Bank Charge depending on bank type
	 */
	public double getBankCharge(String bankType);

}
