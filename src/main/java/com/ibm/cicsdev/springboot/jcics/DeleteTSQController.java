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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ibm.cics.server.CicsConditionException;
import com.ibm.cics.server.TSQ;

@RestController
public class DeleteTSQController {

	/**
	 * The @GetMapping annotation ensures that HTTP GET requests are mapped to the annotated method. 
	 * @throws IOException 
	 **/
	@GetMapping("/deleteTSQs")
	public String deleteTSQs(@RequestParam(value = "tsqName", defaultValue = "ANNE") String tsqName) {

		String response = "";

		// Delete the TSQ
		try {
			response = deleteTSQ(tsqName);
		} catch ( CicsConditionException e) {

			// Print the stack trace
			e.printStackTrace();

			// Return useful information to the user when meeting errors
			return "Oops! Unexpected CICS condition exception: "  + e.getMessage() + ". Please check stderr for details.";
		}		

		return response;
	}

	/**
	 * A method to delete a TSQ.
	 * 
	 * @param tsqName, the name of the TSQ to be deleted 
	 * @return, a message describing the outcome of the action
	 * @throws CicsConditionException
	 */
	private String deleteTSQ(String tsqName) throws CicsConditionException {

		// construct the TSQ object
		TSQ tsqQ = new TSQ();
		tsqQ.setName(tsqName);

		// perform the delete
		tsqQ.delete();

		// return the result of the delete action to the calling servlet
		return "TSQ " + tsqName + " successfully deleted.";
	}
}
