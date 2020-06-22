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

import com.ibm.cics.server.IOErrorException;
import com.ibm.cics.server.ISCInvalidRequestException;
import com.ibm.cics.server.InvalidQueueIdException;
import com.ibm.cics.server.InvalidRequestException;
import com.ibm.cics.server.InvalidSystemIdException;
import com.ibm.cics.server.ItemErrorException;
import com.ibm.cics.server.ItemHolder;
import com.ibm.cics.server.LengthErrorException;
import com.ibm.cics.server.NotAuthorisedException;
import com.ibm.cics.server.TSQ;

@RestController
public class TSQInfoController {
	
	
	/**
	 * The @GetMapping annotation ensures that HTTP GET requests are mapped to the annotated method. 
	 * @throws IOException 
	 **/
	@GetMapping("/TSQInfos")
	public String TSQInfos(@RequestParam(value = "tsqName", defaultValue = "ANNE") String tsqName) {

		// create the object to get the details of the TSQ		
		TSQ tsq = new TSQ();
		tsq.setName(tsqName);
		
		// obtain some information about the TSQ
		String name = "<name>" + getTSQName(tsq) + "</name>";
		String type = "<type>" + getTSQType(tsq) + "</type>";
		String lenStr = "<length>" + getTSQLength(tsq) + "</length>";

		return "The current TSQ information is: " + "Name: " + name + "&& Type: " + type + "&& Length: " + lenStr;			
		
	}
	
	

	/**
	 * A method to obtain the name of the TSQ. 
	 * @return, the name of the TSQ.
	 */
	public String getTSQName(TSQ tsq) {
		return tsq.getName();
	}
	

	/**
	 * A method to get the type of the TSQ.
	 * @return, the type of the TSQ.
	 */
	public String getTSQType(TSQ tsq) {
		return tsq.getType().toString();
	}

	/**
	 * A method to get the number of items (length) of the TSQ. 
	 * @return, the number of items on the TSQ or associated error code.
	 */
	public int getTSQLength(TSQ tsq) {
		
		int length = 0;
		try {
			length = tsq.readItem(1, new ItemHolder());
		} catch (InvalidRequestException e) {			
			return -1;
		} catch (IOErrorException e) {
			return -2;
		} catch (LengthErrorException e) {
			return -3;
		} catch (InvalidSystemIdException e) {
			return -4;
		} catch (ISCInvalidRequestException e) {
			return -5;
		} catch (ItemErrorException e) {
			return -6;
		} catch (NotAuthorisedException e) {
			return -7;
		} catch (InvalidQueueIdException e) {
			return -8;
		}
		return length;
	}

}
