/**
 * 
 */
package com.sdc.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.sdc.model.Schema;
import com.sdc.service.FieldsService;

/**
 * @author arjun
 *
 */
@RestController
public class FieldsController {

	private static final Logger logger = LogManager.getLogger(FieldsController.class);
	
	@Autowired
	FieldsService fieldsService;
	
	@RequestMapping(value="/getSourceFields/{source}" , method=RequestMethod.GET)
	public List<Schema> getSourceFields(@PathVariable("source") String source) {
		ModelAndView modelAndView = new ModelAndView();
		//String source = request.getParameter("source");
		logger.debug("getSourceFields source : "+source);
		List<Schema> schemas = new ArrayList<Schema>();
		try {
		schemas = fieldsService.getSourceFields(source);
		logger.info("schemas : "+schemas.size());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		modelAndView.addObject("schemas",schemas);
		return schemas;
	}
	
	@RequestMapping(value="/getTargetFields/{source}" , method=RequestMethod.GET)
	public List<Schema> getTargetFields(@PathVariable("source") String source) {
		ModelAndView modelAndView = new ModelAndView();
		//String source = request.getParameter("source");
		logger.debug("getSourceFields source : "+source);
		List<Schema> schemas = new ArrayList<Schema>();
		try {
		schemas = fieldsService.getTargetFields(source);
		logger.info("schemas : "+schemas.size());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		modelAndView.addObject("schemas",schemas);
		return schemas;
	}
	
}
