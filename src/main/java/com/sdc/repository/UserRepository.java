/**
 * 
 */
package com.sdc.repository;

import com.sdc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author arjun
 *
 */

@Repository
public class UserRepository {

	private static final Logger logger = LoggerFactory.getLogger("console");
	
	 @Autowired
	 @Qualifier("jdbcTemplate3")
	 private JdbcTemplate jdbcTemplate3;
	 
	 public User getUserDetails(int vendorId) {
		 User user =new User();
		  String sql = "select * from users where VENDOR_ID="+vendorId+"";
		  logger.info("sql : "+sql);
		   user = jdbcTemplate3.query(sql,new ResultSetExtractor<User>() {
			    @Override
			    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
			    	User user =new User();
			    	if(rs.next()) {
			    		user.setUserId(rs.getInt("USERID"));	
			    		user.setUserName(rs.getString("USERNAME"));
			    		user.setPassword(rs.getString("PASSWORD"));
			    		user.setFirstName(rs.getString("FIRST_NAME"));
			    		user.setLastName(rs.getString("LAST_NAME"));
			    		user.setMiddleName(rs.getString("MIDDLE_NAME"));
			    		user.setCompanyName(rs.getString("COMPANYNAME"));
			    		user.setVendorId(rs.getInt("VENDOR_ID"));
			    		user.setVendorExpDate(rs.getString("VENDOR_EXP_DATE"));
			    		user.setEmailId(rs.getString("EMAIL_ID"));
			    		user.setPhone(rs.getString("PHONE"));
			    		user.setActive(rs.getString("ACTIVE"));
			    	}
			    	return user;	
			    }
			});
		   return user;
		 }
	 
}
