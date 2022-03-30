package com.cognizant.pensiondisbursement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Nayan, Akshita, Akhil
 * Entity/model class for PensionDisbursement Response
 * USe of Lombok for default constructor,parameterized constructor and getters and setters
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ProcessPensionResponse {
	/**
	 * instance variables for ProcessPensionResponse entity class
	 */


	/**
	 *  Return Status Code
	 */
	private int pensionStatusCode;

}
