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
import com.sdc.model.User;
import com.sdc.repository.FieldsRepository;
import com.sdc.repository.UserRepository;
import com.sdc.service.UserService;

/**
 * @author arjun
 *
 */
@Service
public class UserServiceImpl implements UserService {

private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getUserDetails(int vendorId) {
		logger.info("vendorId : "+vendorId);
		User user = userRepository.getUserDetails(vendorId);
		return user;
	}
	
}