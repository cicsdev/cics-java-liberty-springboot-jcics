/* Licensed Materials - Property of IBM                                   */
/*                                                                        */
/* SAMPLE                                                                 */
/*                                                                        */
/* (c) Copyright IBM Corp. 2020,2022 All Rights Reserved                       */
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/*                                                                        */

package com.ibm.cicsdev.springboot.jcics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ibm.cics.server.CicsConditionException;
import com.ibm.cics.server.TSQ;

@RestController
public class DeleteTSQController 
{
	/**
	 * The @GetMapping annotation ensures that HTTP GET requests are mapped to the annotated method. 
	 * @throws IOException 
	 **/
	@GetMapping("/delete")
	public String deleteTSQ(@RequestParam(value = "tsq", defaultValue = "IBM") String tsqName) 
	{
		// Delete the TSQ
		try 
		{
			// perform the delete of the TSQ and return success result			
			TSQ tsqQ = new TSQ();
			tsqQ.setName(tsqName);
			tsqQ.delete();
			return "TSQ " + tsqName + " successfully deleted.";
		} 
		catch ( CicsConditionException e) 
		{
			// Return error details
			e.printStackTrace();
			return "Unexpected CICS condition exception: "  + e.getMessage() + ". Check dfhjvmerr for further details.";
		}		
	}
}
