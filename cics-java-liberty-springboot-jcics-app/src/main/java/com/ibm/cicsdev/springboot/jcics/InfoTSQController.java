/* Licensed Materials - Property of IBM                                   */
/*                                                                        */
/* SAMPLE                                                                 */
/*                                                                        */
/* (c) Copyright IBM Corp. 2020.2022 All Rights Reserved                       */
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/*                                                                        */

package com.ibm.cicsdev.springboot.jcics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.cics.server.ItemHolder;
import com.ibm.cics.server.TSQ;

@RestController
public class InfoTSQController 
{	
	/**
	 * The @GetMapping annotation ensures that HTTP GET requests are mapped to the annotated method. 
	 * @throws IOException 
	 **/
	@GetMapping("/info")
	public String infoTSQ(@RequestParam(value = "tsq", defaultValue = "IBM") String tsqName) 
	{
		// Create a JCICS representation of the TSQ object		
		TSQ tsq = new TSQ();
		tsq.setName(tsqName);
		
		// obtain some information about the TSQ
		String name = "<name>" + tsq.getName() + "</name>";
		String type = "<type>" + tsq.getType() + "</type>";
		String lenStr = "<length>" + getTSQLength(tsq) + "</length>";

		return "The current TSQ information is: " + "Name: " + name + ", Type: " + type + ", Length: " + lenStr;			
	}
	
	
	/**
	 * A method to get the number of items (length) of the TSQ. 
	 * @return, the number of items on the TSQ or associated error code.
	 */
	public int getTSQLength(TSQ tsq) 
	{		
		// Read the first item to get the length of the queue
		try 
		{
			return tsq.readItem(1, new ItemHolder());
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
			return -1;
		} 		
	}

}
