/**
 * 
 */
package com.sdc.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sdc.model.DbConnection;
import com.sdc.model.Table;
import com.sdc.model.User;

/**
 * @author arjun
 *
 */
@Repository
public class DBConnectionRepository {

	private static final Logger logger = LoggerFactory.getLogger("console");
	
	 @Autowired
	 @Qualifier("jdbcTemplate3")
	 private JdbcTemplate jdbcTemplate3;

	 
	 public List<DbConnection> getDbConnections() {
		 	String sql ="SELECT ID, DATABASE_TYPE, NAME, DESCRIPTION, URL, DRIVER_CLASS, USERNAME, PASSWORD FROM DB_CONNECTION ";
			
			logger.info(" sql :"+sql);
			List<DbConnection> connections = new ArrayList<DbConnection>(); 
			try {
				connections = jdbcTemplate3.query(sql,new RowMapper<DbConnection>() {
		        @Override
		        public DbConnection mapRow(ResultSet rs, int i) throws SQLException {
		        	DbConnection dbConnection = new DbConnection();
		        	
		        	dbConnection.setId(rs.getInt("ID"));
		        	dbConnection.setType(rs.getString("DATABASE_TYPE"));
		        	dbConnection.setDescription(rs.getString("DESCRIPTION"));
		        	dbConnection.setName(rs.getString("NAME"));
		        	dbConnection.setUrl(rs.getString("URL"));
		        	dbConnection.setDriverclass(rs.getString("DRIVER_CLASS"));
		        	dbConnection.setUsername(rs.getString("USERNAME"));
		        	dbConnection.setPassword(rs.getString("PASSWORD"));
		        	
				return  dbConnection;
		        }
		    });
			
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error(""+e1.getMessage());
			} catch (Exception e) {
				logger.error("",e);
			}
			return connections;
		}
	 public DbConnection getDbConnection(int id) {
		 	String sql ="SELECT ID, DATABASE_TYPE, NAME, DESCRIPTION, URL, DRIVER_CLASS, USERNAME, PASSWORD FROM DB_CONNECTION WHERE ID="+id+"";
			
			logger.info(" sql :"+sql);
			DbConnection connection = new DbConnection(); 
			try {
				connection = jdbcTemplate3.query(sql,new ResultSetExtractor<DbConnection>() {
					    @Override
					    public DbConnection extractData(ResultSet rs) throws SQLException, DataAccessException {
					    	DbConnection dbConnection = new DbConnection();
					    	if(rs.next()) {
					    		dbConnection.setId(rs.getInt("ID"));
					        	dbConnection.setType(rs.getString("DATABASE_TYPE"));
					        	dbConnection.setDescription(rs.getString("DESCRIPTION"));
					        	dbConnection.setName(rs.getString("NAME"));
					        	dbConnection.setUrl(rs.getString("URL"));
					        	dbConnection.setDriverclass(rs.getString("DRIVER_CLASS"));
					        	dbConnection.setUsername(rs.getString("USERNAME"));
					        	dbConnection.setPassword(rs.getString("PASSWORD"));
					    	}
					    	return dbConnection;	
					    }
					});
				
				
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error(""+e1.getMessage());
			} catch (Exception e) {
				logger.error("",e);
			}
			return connection;
		}
	 public int saveDbConnection(DbConnection dbConnection) {
		 String sql="INSERT INTO DB_CONNECTION (DATABASE_TYPE, NAME, DESCRIPTION, URL, DRIVER_CLASS, USERNAME, PASSWORD, CREATED_BY, CREATED, UPDATED_BY, UPDATED) "
		 		+ "VALUES ('"+dbConnection.getType()+"', '"+dbConnection.getName()+"', '"+dbConnection.getDescription()+"', '"+dbConnection.getUrl()+"', '"+dbConnection.getDriverclass() +"', '"+dbConnection.getUsername()+"', '"+dbConnection.getPassword() +"', '"+dbConnection.getUserId() +"', CURRENT_TIMESTAMP, '"+dbConnection.getUserId()+"', CURRENT_TIMESTAMP)";		 		
			 logger.info("sql : "+sql);
			 KeyHolder keyHolder = new GeneratedKeyHolder();
			 try {
			 jdbcTemplate3.update(connection -> {
				    PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				    return ps;
				}, keyHolder);
			 }catch (Exception e) {
				logger.error("error ",e);
			}
					return keyHolder.getKey().intValue();
		}
	 public int updateDbConnection(DbConnection dbConnection) {
		 String sql="UPDATE DB_CONNECTION SET DATABASE_TYPE='"+dbConnection.getType()+"',NAME='"+dbConnection.getName()+"', DESCRIPTION='"+dbConnection.getDescription()+"', URL='"+dbConnection.getUrl()+"', DRIVER_CLASS='"+dbConnection.getDriverclass()+"', USERNAME='"+dbConnection.getUsername()+"', PASSWORD='"+dbConnection.getPassword()+"' WHERE ID="+dbConnection.getId()+"";		 		
			 logger.info("sql : "+sql);
			 try {
			 jdbcTemplate3.update(sql);
			 }catch (Exception e) {
				logger.error("error ",e);
			}
					return dbConnection.getId();
		}
	 public List<String> getDbConnectionNames() {
		 	String sql ="SELECT NAME FROM DB_CONNECTION ";
			
			logger.info(" sql :"+sql);
			List<String> connections = new ArrayList<String>(); 
			try {
				connections = jdbcTemplate3.query(sql,new RowMapper<String>() {
		        @Override
		        public String mapRow(ResultSet rs, int i) throws SQLException {
		        	
				return  rs.getString("NAME");
		        }
		    });
			
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error(""+e1.getMessage());
			} catch (Exception e) {
				logger.error("",e);
			}
			return connections;
		}
	 public DbConnection getDbConnectionByName(String name) {
		 	String sql ="SELECT ID, DATABASE_TYPE, NAME, DESCRIPTION, URL, DRIVER_CLASS, USERNAME, PASSWORD FROM DB_CONNECTION WHERE NAME='"+name+"'";
			
			logger.info(" sql :"+sql);
			DbConnection connection = new DbConnection(); 
			try {
				connection = jdbcTemplate3.query(sql,new ResultSetExtractor<DbConnection>() {
					    @Override
					    public DbConnection extractData(ResultSet rs) throws SQLException, DataAccessException {
					    	DbConnection dbConnection = new DbConnection();
					    	if(rs.next()) {
					    		dbConnection.setId(rs.getInt("ID"));
					        	dbConnection.setType(rs.getString("DATABASE_TYPE"));
					        	dbConnection.setDescription(rs.getString("DESCRIPTION"));
					        	dbConnection.setName(rs.getString("NAME"));
					        	dbConnection.setUrl(rs.getString("URL"));
					        	dbConnection.setDriverclass(rs.getString("DRIVER_CLASS"));
					        	dbConnection.setUsername(rs.getString("USERNAME"));
					        	dbConnection.setPassword(rs.getString("PASSWORD"));
					    	}
					    	return dbConnection;	
					    }
					});
				
				
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error(""+e1.getMessage());
			} catch (Exception e) {
				logger.error("",e);
			}
			return connection;
		}
}
