/**
 * 
 */
package com.sdc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	
	ModelAndView modelAndView = new ModelAndView();
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	
	@Autowired
	DBConnectionService dbConnectionService;
	
	@RequestMapping(value="/getDbConnections" , method=RequestMethod.GET)
	public ModelAndView getDbConnections() {
		
		logger.info("Enter getDbConnections ");
		
		List<DbConnection> connections = dbConnectionService.getDbConnections();
		
		logger.info("connections : "+connections.size());
	
		modelAndView.addObject("connections",connections);
		modelAndView.setViewName("dbConnection");  // redirect name means jsp name
		
		return modelAndView;
	}
	
	@RequestMapping(value="/getDbConnection/{id}" , method=RequestMethod.GET)
	public ModelAndView getDbConnection(@PathVariable("id") int id) {
		
		logger.info("Enter getDbConnection "+id);
		
		DbConnection connection = dbConnectionService.getDbConnection(id);
		
	
		modelAndView.addObject("connection",connection);
		modelAndView.setViewName("dbConnection");  // redirect name means jsp name
		
		return modelAndView;
	}
	
	@RequestMapping(value="/saveDbConnection" , method=RequestMethod.POST)
	public ModelAndView saveDbConnection(@ModelAttribute("dbConnection") DbConnection dbConnection) {
		
		logger.info("Enter saveDbConnection ");
		
		int id = dbConnectionService.saveDbConnection(dbConnection);
	
		modelAndView.setViewName("dbConnection");  // redirect name means jsp name
		
		return modelAndView;
	}
	@RequestMapping(value="/getDbConnectionNames" , method=RequestMethod.GET)
	public List<String> getDbConnectionNames() {
		
		logger.info("Enter getDbConnectionNames ");
		
		List<String> connections = dbConnectionService.getDbConnectionNames();
		
		logger.info("connections : "+connections.size());
	
		
		return connections;
	}
}
