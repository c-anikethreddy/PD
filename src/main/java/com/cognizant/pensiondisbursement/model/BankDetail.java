package com.cognizant.pensiondisbursement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Nayan, Akshita, Akhil
 * Entity/model class for PensionerDetail
 * USe of Lombok for default constructor,parameterized constructor and getters and setters
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BankDetail {
	/**
	 *  instance variables for Bank Detail entity class
	 */
	
	/** Bank Name */
	private String bankName;
	/** Bank Account Number */
	private long accountNumber;
	/** Bank Type */
	private String bankType;
	

}
