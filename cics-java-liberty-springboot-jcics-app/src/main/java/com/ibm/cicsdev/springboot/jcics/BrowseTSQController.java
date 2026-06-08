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

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.cics.server.CicsConditionException;
import com.ibm.cics.server.ItemHolder;
import com.ibm.cics.server.TSQ;

@RestController
public class BrowseTSQController 
{
	/**
	 * The @GetMapping annotation ensures that HTTP GET requests to the /browseTSQs URL path are
	 * mapped to the annotated method.  
	 **/	
	@GetMapping("/browse")
	public String browseTSQ(@RequestParam(value = "tsq", defaultValue = "IBM") String tsqName) 
	{
		// ArrayList of items in the TSQ
		ArrayList<String> tsqItems;

		// Populate the records
		try 
		{		
			tsqItems = readTSQ(tsqName);		
		} 
		catch ( CicsConditionException e) 
		{		
			// Return error details
			e.printStackTrace();
			return "Unexpected CICS condition exception: "  + e.getMessage() + ". Check dfhjvmerr for further details.";
		}		

		return tsqItems.toString();
	}

		
	/**
	 * A method to browse a CICS TSQ
	 * 
	 * @param tsqName, the name of the TSQ to browse 
	 * @return, an ArrayList of records from the TSQ
	 * @throws CicsConditionException
	 */
	private ArrayList<String> readTSQ(String tsqName) throws CicsConditionException 
	{
		// An ArrayList to hold all of the items read from the TSQ
		ArrayList<String> tsqItems = new ArrayList<String>();

		// Create a JCICS representation of the TSQ object
		TSQ tsqQ = new TSQ();			
		tsqQ.setName(tsqName);

		// Create a holder object to store the byte array representing a tsq item
		ItemHolder holder = new ItemHolder();

		// initialise item position
		int itemPos = 1;

		// read the first item into the holder (and get the total number of items on the queue)
		int totalItems = tsqQ.readItem(itemPos, holder);
		
		// add the string representation of the tsq item to the ArrayList
		// getStringValue() - uses the default EBCDIC encoding of CICS region
		tsqItems.add(holder.getStringValue());

		// iterate over, and add, the remaining items to the ArrayList
		while (itemPos < totalItems) 
		{
			tsqQ.readNextItem(holder);			
			tsqItems.add(holder.getStringValue());
			itemPos++;
		}

		// return the Arraylist containing the records from the TSQ
		return tsqItems;
	}
}
