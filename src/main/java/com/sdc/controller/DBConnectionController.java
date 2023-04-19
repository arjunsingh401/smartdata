/**
 * 
 */
package com.sdc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
		modelAndView.setViewName("databaselist");  // redirect name means jsp name
		
		return modelAndView;
	}
	
	@RequestMapping(value="/getDbConnection/{id}" , method=RequestMethod.GET)
	public ModelAndView getDbConnection(HttpSession session, HttpServletRequest request, HttpServletResponse response,@PathVariable("id") int id,Model model) {
		
		logger.info("Enter getDbConnection "+id);
		
		DbConnection list = dbConnectionService.getDbConnection(id);
		model.addAttribute("connection", list);
		modelAndView.addObject("connection",list);
		modelAndView.setViewName("editdatabase");  // redirect name means jsp name
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateDatabase", method = RequestMethod.POST)
	public ModelAndView updateUser(HttpSession session, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("connection") DbConnection connection) {
		String nextPage = "index";
		ModelAndView modelView = new ModelAndView(nextPage);
		return modelView;
	}
	
	@RequestMapping(value="/saveDbConnection" , method=RequestMethod.POST)
	public ModelAndView saveDbConnection(@ModelAttribute("dbConnection") DbConnection dbConnection) {
		
		logger.info("Enter saveDbConnection ");
		
		int id = dbConnectionService.saveDbConnection(dbConnection);
	
		modelAndView.setViewName("dbConnection");  // redirect name means jsp name
		
		return modelAndView;
	}
	
	@RequestMapping(value="/addnNewDatabase" , method=RequestMethod.GET)
	public ModelAndView addnNewDatabase(@ModelAttribute("connection") DbConnection connection) {
		logger.info("Enter addnewdatabase ");
		modelAndView.setViewName("addnewdatabase");  // redirect name means jsp name
		return modelAndView;
	}
	
	@RequestMapping(value="/savenNewDatabase" , method=RequestMethod.POST)
	public ModelAndView savenNewDatabase(@ModelAttribute("connection") DbConnection connection) {
		logger.info("Enter savenNewDatabase ");
		System.out.println("connection desc "+connection.getDescription());
		modelAndView.setViewName("addnewdatabase");  // redirect name means jsp name
		return modelAndView;
	}
}
