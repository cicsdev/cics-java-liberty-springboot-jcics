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
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.cics.server.CicsConditionException;
import com.ibm.cics.server.ItemHolder;
import com.ibm.cics.server.TSQ;

@RestController
public class BrowseTSQController {

	/**
	 * The @GetMapping annotation ensures that HTTP GET requests to the /browseTSQs URL path are
	 * mapped to the annotated method. 
	 * @throws IOException 
	 **/
	
	@GetMapping("/browseTSQs")
	public String browseTSQs(@RequestParam(value = "tsqName", defaultValue = "ANNE") String tsqName) throws IOException {

		// retrieve and store the list of items on the TSQ
		ArrayList<String> records = new ArrayList<String>();

		// browse the records
		try {
			
			records = browseTSQ(tsqName);
			
		} catch ( CicsConditionException e) {
			
			// Print the stack trace
			e.printStackTrace();

			// Return useful information to the users when meeting errors
			return "Oops! Unexpected CICS conditionn exception: "  + e.getMessage() + ". Please check stderr for details.";
		}		

		return records.toString();
	}

	/**
	 * A method to browse a CICS TSQ
	 * 
	 * @param tsqName, the name of the TSQ to browse @return, an ArrayList of records from the TSQ
	 * @throws CicsConditionException
	 * @throws UnsupportedEncodingException 
	 */
	private ArrayList<String> browseTSQ(String tsqName) throws CicsConditionException, UnsupportedEncodingException {
		// An ArrayList to hold all of the items read from TSQ to be returned to the servlet.
		ArrayList<String> records = new ArrayList<String>();

		// construct a JCICS representation of the TSQ object
		TSQ tsqQ = new TSQ();
		tsqQ.setName(tsqName);

		// the holder object will hold the byte array that is read from the TSQ
		ItemHolder holder = new ItemHolder();

		// initialize loop variables
		int itemPos = 1;
		int totalItems = 0;
		String recordStr = null;

		// read an item and store the total number of items on the queue
		totalItems = tsqQ.readItem(itemPos, holder);

		// get string from the holder byte[] -  defaults to using EBCDIC encoding of CICS region
		// then add the record to the ArrayList to be returned
		recordStr = holder.getStringValue();		
		records.add(recordStr);

		// iterate over the remaining items and add  to the ArrayList
		while (itemPos < totalItems) {
			tsqQ.readNextItem(holder);
			recordStr = holder.getStringValue();
			records.add(recordStr);
			itemPos++;
		}

		// return the arraylist containing the records from the TSQ
		return records;
	}
}
