/**
 * 
 */
package com.sdc.serviceImpl;

import com.sdc.model.Schema;
import com.sdc.repository.FieldsRepository;
import com.sdc.service.FieldsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author arjun
 *
 */
@Service
public class FieldsServiceImpl implements FieldsService {

	private static final Logger logger = LoggerFactory.getLogger(FieldsServiceImpl.class);
	
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
