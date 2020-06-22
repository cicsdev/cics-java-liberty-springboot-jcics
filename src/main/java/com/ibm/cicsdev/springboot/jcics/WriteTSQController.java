/* Licensed Materials - Property of IBM                                   */
/*                                                                        */
/* SAMPLE                                                                 */
/*                                                                        */
/* (c) Copyright IBM Corp. 2020 All Rights Reserved                       */
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/*                                                                        */

package com.ibm.cicsdev.springboot.jcics;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.cics.server.CicsConditionException;
import com.ibm.cics.server.TSQ;

@RestController
public class WriteTSQController {

	/**
	 * The @GetMapping annotation ensures that HTTP GET requests are mapped to the annotated method. 
	 * @throws IOException 
	 **/
	@GetMapping("/writeTSQs")
	public String writeTSQs(@RequestParam(value = "tsqName", defaultValue = "ANNE") String tsqName,
			@RequestParam(value = "tsqContent", defaultValue = "Spring greetings from CICS!") String tsqContent) {

		String response = "";

		// Write to the TSQ
		try {
			response = writeTSQ(tsqName, tsqContent);
		} catch ( CicsConditionException | UnsupportedEncodingException e) {

			// Print the stack trace
			e.printStackTrace();

			// Return useful information to the user when meeting errors
			return "Oops! Unexpected CICS condition exception: "  + e.getMessage() + ". Please check stderr for details.";
		}
		return response;
	}

	/**
	 * A method to write a record to a TSQ
	 * 
	 * @param tsqName, the name of the TSQ to be written to
	 * @param record, the data to be written to the TSQ
	 * @return, the result of the write 
	 * @throws UnsupportedEncodingException 
	 * @throws CicsConditionException	   
	 */
	private String writeTSQ(String tsqName, String record) throws CicsConditionException, UnsupportedEncodingException{
		
		// Construct the TSQ object
		TSQ tsqQ = new TSQ();
		tsqQ.setName(tsqName);

		// the result of writing an item to the TSQ
		String result = "";

		// write the record to the TSQ
		tsqQ.writeString(record);		
		result = "Record written to TSQ " + tsqName + ".";

		// return the result to the calling servlet
		return result;
	}
}
