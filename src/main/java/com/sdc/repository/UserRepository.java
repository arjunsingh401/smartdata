/**
 * 
 */
package com.sdc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author arjun
 *
 */

@Repository
public class UserRepository {

	 @Autowired
	 @Qualifier("jdbcTemplate1")
	 private JdbcTemplate jdbcTemplate1;
	
	 @Autowired
	 @Qualifier("jdbcTemplate2")
	 private JdbcTemplate jdbcTemplate2;
	 
	 @Autowired
	 @Qualifier("jdbcTemplate3")
	 private JdbcTemplate jdbcTemplate3;
	 
	 public void getAllUser() {
		  String sql1 = "select * from users where userid=0";
		  //get users list from db1
		  List<String> list1 = jdbcTemplate1.query(sql1, new RowMapper<String>(){
			  
			  @Override
			  public String mapRow(ResultSet rs, int i) throws SQLException{
				  System.out.println("sql1 username : "+rs.getString("USERNAME"));
				return rs.getString("USERNAME");
			  }
			  
		  });
		  
		  String sql2 = "select * from users where userid=130089";
		  //get users list from db2
		  List<String> list2 = jdbcTemplate2.query(sql2, new RowMapper<String>(){
			  
			  @Override
			  public String mapRow(ResultSet rs, int i) throws SQLException{
				  System.out.println("sql2 username : "+rs.getString("USERNAME"));
				return rs.getString("USERNAME");
			  }
			  
		  });
		  
		  String sql3 = "select * from users where userid=1";
		  //get users list from db2
		  List<String> list3 = jdbcTemplate3.query(sql2, new RowMapper<String>(){
			  
			  @Override
			  public String mapRow(ResultSet rs, int i) throws SQLException{
				  System.out.println("sql3 username : "+rs.getString("USERNAME"));
				return rs.getString("USERNAME");
			  }
			  
		  });
		  
		 }
	 
}
