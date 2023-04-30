/**
 * 
 */
package com.sdc.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdc.BatchStatus;
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
		 	String sql ="select ID, NAME, DESCRIPTION, TOTAL_ROWS, PENDING_ROWS, FAILED_RECORDS, UPDATED, STATUS from JOBS ORDER BY ID DESC";
			
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
		        	//jobs.setTotalTime(rs.getString("TOTAL_TIME"));
		        	jobs.setPendingRows(rs.getString("PENDING_ROWS"));
		        	//jobs.setPendingTime(rs.getString("PENDING_TIME"));
		        	//jobs.setCreatedBy(rs.getString("CREATED_BY"));
		        	//jobs.setSourceDbName(rs.getString("SOURCE_SCHEMA_NAME"));
		        	//jobs.setTargetDbName(rs.getString("TARGET_SCHEMA_NAME"));
					jobs.setUpdated(rs.getString("UPDATED"));
					jobs.setStatus(rs.getString("STATUS"));
					jobs.setFailedRecords(rs.getLong("FAILED_RECORDS"));
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

	public int stopJob(String jobId) {
		 String sql = "UPDATE JOBS SET STATUS='" + BatchStatus.TERMINATED + "' WHERE ID=?";
		 logger.info(" sql :"+sql);

		try {
			return jdbcTemplate3.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, jobId);
				return ps;
			});
		}catch (Exception e) {
			logger.error("error ",e);
		}
		return 0;


	}
}
