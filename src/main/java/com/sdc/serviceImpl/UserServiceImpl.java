/**
 * 
 */
package com.sdc.serviceImpl;

import com.sdc.model.User;
import com.sdc.repository.UserRepository;
import com.sdc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author arjun
 *
 */
@Service
public class UserServiceImpl implements UserService {

private static final Logger logger = LoggerFactory.getLogger("console");
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getUserDetails(int vendorId) {
		logger.info("vendorId : "+vendorId);
		User user = userRepository.getUserDetails(vendorId);
		return user;
	}
	
}