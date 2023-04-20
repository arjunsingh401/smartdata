/**
 * 
 */
package com.sdc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sdc.model.DbConnection;
import com.sdc.service.DBConnectionService;

/**
 * @author arjun
 *
 */
@RestController
public class DBConnectionController {

	private static final Logger logger = LogManager.getLogger(DBConnectionController.class);
	
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	
	@Autowired
	DBConnectionService dbConnectionService;
	
	@RequestMapping(value="/getDbConnections" , method=RequestMethod.GET)
	public ModelAndView getDbConnections() {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("Enter getDbConnections ");
		
		List<DbConnection> connections = dbConnectionService.getDbConnections();
		
		logger.info("connections : "+connections.size());
	
		modelAndView.addObject("connections",connections);
		modelAndView.setViewName("databaselist");  // redirect name means jsp name
		
		return modelAndView;
	}
	
	@RequestMapping(value="/getDbConnection/{id}" , method=RequestMethod.GET)
	public ModelAndView getDbConnection(HttpSession session, HttpServletRequest request, HttpServletResponse response,@PathVariable("id") int id,@ModelAttribute("connection") DbConnection connection) {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("Enter getDbConnection "+id);
		
		connection = dbConnectionService.getDbConnection(id);
		modelAndView.addObject("connection",connection);
		modelAndView.setViewName("editdatabase");  // redirect name means jsp name
		
		return modelAndView;
	}
	
	@RequestMapping(value="/saveDbConnection" , method=RequestMethod.POST)
	public ModelAndView saveDbConnection(@ModelAttribute("connection") DbConnection connection) {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("Enter saveDbConnection ");
		List<String> connections = dbConnectionService.getDbConnectionNames().stream().filter(n -> n.equalsIgnoreCase(connection.getName())).collect(Collectors.toUnmodifiableList());
		if(connections.size() > 0 && connection.getId() == 0) {
			modelAndView.setViewName("addnewdatabase"); 
			modelAndView.addObject("errorMsg", "DB Name "+connection.getName()+" already exists");
		} else {
			int id = dbConnectionService.saveDbConnection(connection);
			modelAndView.setViewName("redirect:/getDbConnections");  // redirect name means jsp name
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/addnNewDatabase" , method=RequestMethod.GET)
	public ModelAndView addnNewDatabase(@ModelAttribute("connection") DbConnection connection) {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("Enter addnewdatabase ");
		modelAndView.setViewName("addnewdatabase");  // redirect name means jsp name
		return modelAndView;
	}
	
	@RequestMapping(value="/savenNewDatabase" , method=RequestMethod.POST)
	public ModelAndView savenNewDatabase(@ModelAttribute("connection") DbConnection connection) {
		ModelAndView modelAndView = new ModelAndView();
		logger.info("Enter savenNewDatabase ");
		System.out.println("connection desc "+connection.getDescription());
		modelAndView.setViewName("addnewdatabase");  // redirect name means jsp name
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
