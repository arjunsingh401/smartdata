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
}
