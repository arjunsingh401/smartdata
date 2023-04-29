/**
 * 
 */
package com.sdc.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.sdc.model.Jobs;
import com.sdc.service.JobsService;
import com.sdc.service.UserService;

/**
 * @author arjun
 *
 */
@RestController
public class JobsController {

	private static final Logger logger = LoggerFactory.getLogger(JobsController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	JobsService jobsService; 
	
	@RequestMapping(value="/getJobs" , method=RequestMethod.GET)
	public ModelAndView getJobs(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		String nextPage = "redirect:/";
		logger.info("Enter getJobs ");
		if(!userService.reloginRequired(request)) {
			List<Jobs> jobs = jobsService.getJobs();
			logger.info("jobs : "+jobs.size());
			modelAndView.addObject("jobs",jobs);
			nextPage = "jobs";  
		}
		modelAndView.setViewName(nextPage);  // redirect name means jsp name
		return modelAndView;
	}
}