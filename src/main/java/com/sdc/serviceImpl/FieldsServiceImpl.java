/**
 * 
 */
package com.sdc.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sdc.model.Schema;
import com.sdc.repository.FieldsRepository;
import com.sdc.service.FieldsService;


/**
 * @author arjun
 *
 */
@Service
public class FieldsServiceImpl implements FieldsService {

	private static final Logger logger = LogManager.getLogger(FieldsServiceImpl.class);
	
	@Autowired
	FieldsRepository fieldRepository;
	
	@Override
	public List<Schema> getSourceFields(String source ) {
		logger.info("source : "+source);
		List<Schema> schemas = new ArrayList<Schema>();
		try {
		schemas= fieldRepository.getSourceFields(source);
		logger.info("schemas : "+schemas.size());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		return schemas;
	}
	@Override
	public List<Schema> getTargetFields(String source ) {
		logger.info("source : "+source);
		List<Schema> schemas = new ArrayList<Schema>();
		try {
		schemas= fieldRepository.getTargetFields(source);
		logger.info("schemas : "+schemas.size());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("",e);
		}
		return schemas;
	}
	
}
