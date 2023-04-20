/**
 * 
 */
package com.sdc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sdc.model.User;
import com.sdc.service.UserService;

/**
 * @author arjun
 *
 */
@Controller
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	ModelAndView modelAndView = new ModelAndView();
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/")
	public String homeIndex() {
		
		//logger.info("index");
		return "index";
	}
	
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("user") User user) {
		
		logger.info("login api starts ");
		logger.info("user : "+user.toString());
		
		User userDetails= userService.getUserDetails(user.getVendorId());
		logger.info("userDetails : "+userDetails);
		if(userDetails.getVendorId()==0) {
			userDetails.setDisplayErrorMsg("Please enter valid Vendor Id");
			modelAndView.setViewName("index");
			modelAndView.addObject("loginForm", userDetails);
		}else if(userDetails.getUserName() !=null && !userDetails.getUserName().equalsIgnoreCase(user.getUserName())) {
			userDetails.setDisplayErrorMsg("Please enter valid User Name");
			modelAndView.setViewName("index");
			modelAndView.addObject("loginForm", userDetails);
		}else if(userDetails.getPassword()!=null && !userDetails.getPassword().equalsIgnoreCase(user.getPassword())) {
			userDetails.setDisplayErrorMsg("Please enter valid Password");
			modelAndView.setViewName("index");
			modelAndView.addObject("loginForm", userDetails);
		}else {
			session.setAttribute("userId", userDetails.getUserId());
			modelAndView.setViewName("home");
			modelAndView.addObject("loginForm", userDetails);
		}
		
		
		
		return modelAndView;
	}
	
	@RequestMapping("/home")
	public String homePage() {
		logger.info("home");
		//user.getAllUser();
		return "home";
	}
	
	
	@RequestMapping("/configdatabase")
	public String configDatabase() {
		
		logger.info("configdatabase");
		//user.getAllUser();
		return "configdatabase";
	}
	
}
