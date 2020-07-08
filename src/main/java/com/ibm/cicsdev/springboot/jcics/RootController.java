package com.ibm.cicsdev.springboot.jcics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController 
{
		@GetMapping("/")
		public String root() 
		{						
			return "Spring Boot JCICS REST sample usage: /browse{tsq=name}, /write{tsq=name&item=content}, /info{tsq=name}, /delete{tsq=name}";
		}	
}
