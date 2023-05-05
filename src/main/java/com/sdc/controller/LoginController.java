/**
 * 
 */
package com.sdc.controller;

import com.sdc.model.User;
import com.sdc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author arjun
 *
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger("console");
	
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
		ModelAndView modelAndView = new ModelAndView();
		
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
			session.setAttribute("userName", userDetails.getUserName());
			modelAndView.setViewName("home");
			modelAndView.addObject("loginForm", userDetails);
		}
		
		
		
		return modelAndView;
	}
	
	@RequestMapping("/home")
	public String homePage(HttpServletRequest request) {
		String nextPage = "redirect:/";
		if(!userService.reloginRequired(request)) {
			nextPage = "home";
		} 
		
		return nextPage;
	}
	
	
	@RequestMapping("/configdatabase")
	public String configDatabase(HttpServletRequest request) {
		String nextPage = "redirect:/";
		if(!userService.reloginRequired(request)) {
			logger.info("configdatabase");
			//user.getAllUser();
			nextPage  = "configdatabase";
		}
		return nextPage;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
}
