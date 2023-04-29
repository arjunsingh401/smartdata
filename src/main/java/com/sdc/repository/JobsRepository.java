/**
 * 
 */
package com.sdc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sdc.model.DbConnection;
import com.sdc.model.Jobs;

/**
 * @author arjun
 *
 */
@Repository
public class JobsRepository {

	private static final Logger logger = LoggerFactory.getLogger(JobsRepository.class);
	
	 @Autowired
	 @Qualifier("jdbcTemplate3")
	 private JdbcTemplate jdbcTemplate3;
	
	 public List<Jobs> getJobs() {
		 	String sql ="select J.ID,  J.NAME,  MF.SOURCE_SCHEMA_NAME+'.'+MF.SOURCE_TABLE_NAME+' ---> '+MF.TARGET_SCHEMA_NAME+'.'+MF.TARGET_TABLE_NAME as DESCRIPTION, J.TOTAL_ROWS, J.TOTAL_TIME, J.PENDING_ROWS, J.PENDING_TIME, J.CREATED_BY,MF.SOURCE_SCHEMA_NAME,TARGET_SCHEMA_NAME from JOBS J JOIN MAPPING_FIELDS MF ON MF.ID=J.MAPPING_ID ORDER BY J.ID DESC";		 			
			
			logger.info(" sql :"+sql);
			List<Jobs> jobs = new ArrayList<Jobs>(); 
			try {
				jobs = jdbcTemplate3.query(sql,new RowMapper<Jobs>() {
		        @Override
		        public Jobs mapRow(ResultSet rs, int i) throws SQLException {
		        	Jobs jobs = new Jobs();
		        	
		        	jobs.setId(rs.getInt("ID"));
		        	jobs.setName(rs.getString("NAME"));
		        	jobs.setDescription(rs.getString("DESCRIPTION"));
		        	jobs.setTotalRows(rs.getString("TOTAL_ROWS"));
		        	jobs.setTotalTime(rs.getString("TOTAL_TIME"));
		        	jobs.setPendingRows(rs.getString("PENDING_ROWS"));
		        	jobs.setPendingTime(rs.getString("PENDING_TIME"));
		        	jobs.setCreatedBy(rs.getString("CREATED_BY"));
		        	jobs.setSourceDbName(rs.getString("SOURCE_SCHEMA_NAME"));
		        	jobs.setTargetDbName(rs.getString("TARGET_SCHEMA_NAME"));
		        	
				return  jobs;
		        }
		    });
			
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error(""+e1.getMessage());
			} catch (Exception e) {
				logger.error("",e);
			}
			return jobs;
		}
	
}
