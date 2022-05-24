/* (c) Copyright IBM Corp. 2020,2022 All Rights Reserved                       */
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/* 
 */

package com.ibm.cicsdev.springboot.jcics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RootController 
{
    
        /**
        * Provide a root URL for usage: information  
        **/
        @GetMapping("/")
        public String root() 
        {                        
            return "Spring Boot JCICS REST sample usage: /browse?{tsq=name}, /write?{tsq=name&item=content}, /info?{tsq=name}, /delete?{tsq=name}";
        }    
}
