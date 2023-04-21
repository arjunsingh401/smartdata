/**
 * 
 */
package com.sdc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sdc.model.User;

/**
 * @author arjun
 *
 */
public interface UserService {

	
	public User getUserDetails(int vendorId);
	
	default boolean reloginRequired(HttpServletRequest request) {
		boolean reloginRequired = false;
		HttpSession session = request.getSession(false);
		try {
			if (session.getAttribute("userId") == null) {
				reloginRequired = true;
				request.setAttribute("error", "Session expired. Please login again");
			}
		} catch (Exception e) {
			reloginRequired = true;
		}
		return reloginRequired;
	}
	
}
