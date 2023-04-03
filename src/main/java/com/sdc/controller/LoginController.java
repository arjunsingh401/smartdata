/**
 * 
 */
package com.sdc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arjun
 *
 */
@Controller
public class LoginController {


	
	@RequestMapping("/")
	public String homeIndex() {
		
		System.out.println("hello world");
		return "index";
	}
	
}
