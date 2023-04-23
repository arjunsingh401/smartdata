/**
 * 
 */
package com.sdc.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sdc.model.DbConnection;

/**
 * @author arjun
 *
 */
public interface DBConnectionService {

	List<DbConnection> getDbConnections();

	DbConnection getDbConnection(int id);

	int saveDbConnection(DbConnection dbConnection);

	List<String> getDbConnectionNames();

	JdbcTemplate jdbcTemplate(String driverClassName, String jdbcUrl, String username, String password)
			throws IllegalAccessException, InvocationTargetException, InstantiationException;

}
