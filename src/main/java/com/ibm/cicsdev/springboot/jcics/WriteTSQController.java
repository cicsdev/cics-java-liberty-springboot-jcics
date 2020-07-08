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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.cics.server.CicsConditionException;
import com.ibm.cics.server.TSQ;

@RestController
public class WriteTSQController 
{
	/**
	 * The @GetMapping annotation ensures that HTTP GET requests are mapped to the annotated method. 
	 * @throws IOException 
	 **/
	@GetMapping({"/write", "/writeTSQs", "/writeTSQ"})
	public String writeTSQ(@RequestParam(value = "tsq", defaultValue = "ANNE") String tsqName,
			@RequestParam(value = "item", defaultValue = "Hello from Spring Boot in CICS") String tsqItemContent) 
	{		
		// Write to the TSQ
		try 
		{
			// write the supplied item/record to the TSQ
			TSQ tsqQ = new TSQ();
			tsqQ.setName(tsqName);		
			tsqQ.writeString(tsqItemContent);
			return "Item '" + tsqItemContent + "' written to TSQ '" + tsqName + "'.";
		} 
		catch (CicsConditionException e) 
		{
			// Return error details
			e.printStackTrace();
			return "Unexpected CICS condition exception: "  + e.getMessage() + ". Check dfhjvmerr for further details.";
		}		
	}
}
