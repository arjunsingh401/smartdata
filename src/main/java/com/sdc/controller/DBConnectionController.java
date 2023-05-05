/**
 * 
 */
package com.sdc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sdc.model.DbConnection;
import com.sdc.service.DBConnectionService;
import com.sdc.service.UserService;

/**
 * @author arjun
 *
 */
@RestController
public class DBConnectionController {

	private static final Logger logger = LoggerFactory.getLogger("console");
	
	@Autowired
	HttpSession session;
	
	@Autowired
	DBConnectionService dbConnectionService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/getDbConnections" , method=RequestMethod.GET)
	public ModelAndView getDbConnections(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String nextPage = "redirect:/";
		if(!userService.reloginRequired(request)) {
			logger.info("Enter getDbConnections ");
			
			List<DbConnection> connections = dbConnectionService.getDbConnections();
			
			logger.info("connections : "+connections.size());
		
			modelAndView.addObject("connections",connections);
			nextPage = "databaselist";  
		}
		modelAndView.setViewName(nextPage);  // redirect name means jsp name
		
		return modelAndView;
	}
	
	@RequestMapping(value="/getDbConnection/{id}" , method=RequestMethod.GET)
	public ModelAndView getDbConnection(HttpSession session, HttpServletRequest request, HttpServletResponse response,@PathVariable("id") int id,@ModelAttribute("connection") DbConnection connection) {
		ModelAndView modelAndView = new ModelAndView();
		String nextPage = "redirect:/";
		if(!userService.reloginRequired(request)) {
			logger.info("Enter getDbConnection "+id);
			
			connection = dbConnectionService.getDbConnection(id);
			modelAndView.addObject("connection",connection);
			nextPage = "editdatabase"; 
		}
		modelAndView.setViewName(nextPage); 
		
		return modelAndView;
	}
	
	@RequestMapping(value="/saveDbConnection" , method=RequestMethod.POST)
	public ModelAndView saveDbConnection(HttpServletRequest request,@ModelAttribute("connection") DbConnection connection) {
		ModelAndView modelAndView = new ModelAndView();
		String nextPage = "redirect:/";
		if(!userService.reloginRequired(request)) {
			logger.info("Enter saveDbConnection ");
			List<String> connections = dbConnectionService.getDbConnectionNames().stream().filter(n -> n.equalsIgnoreCase(connection.getName())).collect(Collectors.toUnmodifiableList());
			if(connections.size() > 0 && connection.getId() == 0) {
				nextPage = "addnewdatabase"; 
				modelAndView.addObject("errorMsg", "DB Name "+connection.getName()+" already exists");
			} else {
				dbConnectionService.saveDbConnection(connection);
				nextPage = "redirect:/getDbConnections";  // redirect name means jsp name
			}
		}
		modelAndView.setViewName(nextPage); 
		return modelAndView;
	}
	
	@RequestMapping(value="/addnNewDatabase" , method=RequestMethod.GET)
	public ModelAndView addnNewDatabase(HttpServletRequest request,@ModelAttribute("connection") DbConnection connection) {
		ModelAndView modelAndView = new ModelAndView();
		String nextPage = "redirect:/";
		if(!userService.reloginRequired(request)) {
			logger.info("Enter addnewdatabase ");
			nextPage = "addnewdatabase";  // redirect name means jsp name
		}
		modelAndView.setViewName(nextPage);  // redirect name means jsp name
		return modelAndView;
	}
	
	@RequestMapping(value="/savenNewDatabase" , method=RequestMethod.POST)
	public ModelAndView savenNewDatabase(HttpServletRequest request,@ModelAttribute("connection") DbConnection connection) {
		ModelAndView modelAndView = new ModelAndView();
		String nextPage = "redirect:/";
		if(!userService.reloginRequired(request)) {
			logger.info("Enter savenNewDatabase ");
			System.out.println("connection desc "+connection.getDescription());
			nextPage = "addnewdatabase";
		}
		modelAndView.setViewName(nextPage);
		return modelAndView;
	}
	
	@RequestMapping(value="/getDbConnectionNames" , method=RequestMethod.GET)
	public List<String> getDbConnectionNames() {
		
		logger.info("Enter getDbConnections ");
		
		List<String> connections = dbConnectionService.getDbConnectionNames();
		
		logger.info("connections : "+connections.size());
	
		return connections;
	}
	
}
