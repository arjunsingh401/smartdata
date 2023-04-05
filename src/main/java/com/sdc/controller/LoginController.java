/**
 * 
 */
package com.sdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdc.repository.UserRepository;

/**
 * @author arjun
 *
 */
@Controller
public class LoginController {

	@Autowired
	UserRepository user;
	
	@RequestMapping("/")
	public String homeIndex() {
		
		System.out.println("hello world");
		user.getAllUser();
		return "index";
	}
	
}
