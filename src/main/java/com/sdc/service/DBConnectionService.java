/**
 * 
 */
package com.sdc.service;

import java.util.List;

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

}
