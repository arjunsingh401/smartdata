/**
 * 
 */
package com.sdc.serviceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sdc.controller.LoginController;
import com.sdc.model.DbConnection;
import com.sdc.repository.DBConnectionRepository;
import com.sdc.service.DBConnectionService;

/**
 * @author arjun
 *
 */
@Service
public class DBConnectionServiceImpl implements DBConnectionService {

	private static final Logger logger = LogManager.getLogger(DBConnectionServiceImpl.class);
	
	@Autowired
	DBConnectionRepository dBConnectionRepository;
	
	@Override
	public List<DbConnection> getDbConnections(){
		
		List<DbConnection> connections = new ArrayList<DbConnection>();
		try {
		connections = dBConnectionRepository.getDbConnections();
		}catch (Exception e) {
			logger.error("",e);
		}
		return connections;
	}

	@Override
	public DbConnection getDbConnection(int id){
		
		DbConnection connection = new DbConnection();
		try {
		connection = dBConnectionRepository.getDbConnection(id);
		}catch (Exception e) {
			logger.error("",e);
		}
		return connection;
	}
	
	@Override
	public int saveDbConnection(DbConnection dbConnection){
		int id=0;
		try {
			if(dbConnection.getId() >0) {
				id = dBConnectionRepository.updateDbConnection(dbConnection);
			}else {
				id = dBConnectionRepository.saveDbConnection(dbConnection);		
			}
		}catch (Exception e) {
			logger.error("",e);
		}
		return id;
	}
	@Override
	public List<String> getDbConnectionNames(){
		
		List<String> connections = new ArrayList<String>();
		try {
		connections = dBConnectionRepository.getDbConnectionNames();
		}catch (Exception e) {
			logger.error("",e);
		}
		return connections;
	}
	
	@Override
	public JdbcTemplate jdbcTemplate( String driverClassName,  String jdbcUrl, String username, String password)  throws IllegalAccessException, InvocationTargetException, InstantiationException {
	     // extract this 4 parameters using your own logic

		 // final String driverClassName = "org.h2.Driver";
	     //final String jdbcUrl = "jdbc:h2:mem:test";
	     //final String username = "sa";
	     //final String password = "";
		 
	     // Build dataSource manually:
	    // final Class<?> driverClass = ClassUtils.resolveClassName(driverClassName, this.getClass().getClassLoader());
	     //final Driver driver = (Driver) ClassUtils.getConstructorIfAvailable(driverClass).newInstance();
	    // final DataSource dataSource = new SimpleDriverDataSource(driver, jdbcUrl, username, password);
	     // or using DataSourceBuilder:
	     final DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url(jdbcUrl).username(username).password(password).build();
	     // and make the jdbcTemplate
	     return new JdbcTemplate(dataSource);
	 }
	
}
