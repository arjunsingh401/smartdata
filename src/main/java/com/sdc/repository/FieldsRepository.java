/**
 * 
 */
package com.sdc.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.sdc.model.Column;
import com.sdc.model.MappingData;
import com.sdc.model.Schema;
import com.sdc.model.Table;
import com.sdc.service.DBConnectionService;
import com.sdc.model.DbConnection;
/**
 * @author arjun
 *
 */
@Repository
public class FieldsRepository {

	private static final Logger logger = LogManager.getLogger(FieldsRepository.class);
	
	 @Autowired
	 @Qualifier("jdbcTemplate1")
	 private JdbcTemplate jdbcTemplate1;
	
	 @Autowired
	 @Qualifier("jdbcTemplate2")
	 private JdbcTemplate jdbcTemplate2;
	 
	 @Autowired
	 @Qualifier("jdbcTemplate3")
	 private JdbcTemplate jdbcTemplate3;
	 
	 @Autowired
	 DBConnectionRepository dBConnectionRepository;
	 
	 @Autowired
	 DBConnectionService dBConnectionService;
	 
	 public List<Schema> getSourceFields(String source) {
		 	String sql ="SELECT name AS schema_name,  schema_id FROM sys.schemas ORDER BY name ";
			
			logger.info("schema sql :"+sql);
			List<Schema> schemas = new ArrayList<Schema>(); 
			
			try {
				DbConnection connection = dBConnectionRepository.getDbConnectionByName(source);
				JdbcTemplate jdbcTemplate = dBConnectionService.jdbcTemplate(connection.getDriverclass(), connection.getUrl(), connection.getUsername(), connection.getPassword());
				schemas = jdbcTemplate.query(sql,new RowMapper<Schema>() {
		        @Override
		        public Schema mapRow(ResultSet rs, int i) throws SQLException {
		        	Schema schema = new Schema();
		        	schema.setSchemaName(rs.getString("schema_name"));
		        	schema.setTables(getSourceTables(schema.getSchemaName(), jdbcTemplate));
				return  schema;
		        }
		    });
			
		     logger.info("# schema size - " + schemas.size());
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error("",e1);
			} catch (Exception e) {
				logger.error("",e);
			}
			return schemas;
		}
	 
	 public List<Table> getSourceTables(String schema,JdbcTemplate jdbcTemplate) {
		 	String sql ="SELECT * FROM information_schema.tables WHERE TABLE_TYPE='BASE TABLE' and TABLE_SCHEMA = '"+schema+"'";
			
			logger.info("table sql :"+sql);
			List<Table> tables = new ArrayList<Table>(); 
			try {
				tables = jdbcTemplate.query(sql,new RowMapper<Table>() {
		        @Override
		        public Table mapRow(ResultSet rs, int i) throws SQLException {
		        	Table table = new Table();
		        	table.setSchema(rs.getString("TABLE_SCHEMA"));
		        	table.setName(rs.getString("TABLE_NAME"));
		        	table.setColumns(getSourceColumns(table.getName(),jdbcTemplate));
		        	
				return  table;
		        }
		    });
			
		     logger.info("# table size - " + tables.size());
			} catch(EmptyResultDataAccessException e1) {
				logger.info("error in table ");
				e1.printStackTrace();
				logger.error(""+e1.getMessage());
			} catch (Exception e) {
				logger.info("error in table 90 ");
				logger.error("",e);
			}
			return tables;
		}
	 
	 public List<Column> getSourceColumns(String table,JdbcTemplate jdbcTemplate) {
		 	String sql ="SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE  TABLE_NAME = '"+table+"'";
			
			//logger.info("column sql :"+sql);
			List<Column> columns = new ArrayList<Column>(); 
			try {
				columns = jdbcTemplate.query(sql,new RowMapper<Column>() {
		        @Override
		        public Column mapRow(ResultSet rs, int i) throws SQLException {
		        	Column column = new Column();
		        	column.setSchema(rs.getString("TABLE_SCHEMA"));
		        	column.setTable(rs.getString("TABLE_NAME"));
		        	column.setName(rs.getString("COLUMN_NAME"));
		        	column.setDataType(rs.getString("DATA_TYPE"));
		        	column.setLength(rs.getString("CHARACTER_MAXIMUM_LENGTH"));
				return  column;
		        }
		    });
			
		    // logger.info("# columns size - " + columns.size());
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error("",e1);
			} catch (Exception e) {
				logger.error("",e);
			}
			return columns;
		}
	 public List<Schema> getTargetFields(String source) {
		 	String sql ="SELECT name AS schema_name,  schema_id FROM sys.schemas ORDER BY name ";
			
			logger.info("schema sql :"+sql);
			List<Schema> schemas = new ArrayList<Schema>(); 
			try {
				DbConnection connection = dBConnectionRepository.getDbConnectionByName(source);
				JdbcTemplate jdbcTemplate = dBConnectionService.jdbcTemplate(connection.getDriverclass(), connection.getUrl(), connection.getUsername(), connection.getPassword());
				schemas = jdbcTemplate.query(sql,new RowMapper<Schema>() {
		        @Override
		        public Schema mapRow(ResultSet rs, int i) throws SQLException {
		        	Schema schema = new Schema();
		        	schema.setSchemaName(rs.getString("schema_name"));
		        	schema.setTables(getTargetTables(schema.getSchemaName(),jdbcTemplate));
				return  schema;
		        }
		    });
			
		     logger.info("# schema size - " + schemas.size());
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error("",e1);
			} catch (Exception e) {
				logger.error("",e);
			}
			return schemas;
		}
	 
	 public List<Table> getTargetTables(String schema,JdbcTemplate jdbcTemplate) {
		 	String sql ="SELECT * FROM information_schema.tables WHERE TABLE_TYPE='BASE TABLE' and TABLE_SCHEMA = '"+schema+"'";
			
			logger.info("table sql :"+sql);
			List<Table> tables = new ArrayList<Table>(); 
			try {
				tables = jdbcTemplate.query(sql,new RowMapper<Table>() {
		        @Override
		        public Table mapRow(ResultSet rs, int i) throws SQLException {
		        	Table table = new Table();
		        	table.setSchema(rs.getString("TABLE_SCHEMA"));
		        	table.setName(rs.getString("TABLE_NAME"));
		        	table.setColumns(getTargetColumns(table.getName(), jdbcTemplate));
		        	
				return  table;
		        }
		    });
			
		     logger.info("# table size - " + tables.size());
			} catch(EmptyResultDataAccessException e1) {
				logger.info("error in table ");
				e1.printStackTrace();
				logger.error(""+e1.getMessage());
			} catch (Exception e) {
				logger.info("error in table 90 ");
				logger.error("",e);
			}
			return tables;
		}
	 
	 public List<Column> getTargetColumns(String table,JdbcTemplate jdbcTemplate) {
		 	String sql ="SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE  TABLE_NAME = '"+table+"'";
			
			//logger.info("column sql :"+sql);
			List<Column> columns = new ArrayList<Column>(); 
			try {
				columns = jdbcTemplate.query(sql,new RowMapper<Column>() {
		        @Override
		        public Column mapRow(ResultSet rs, int i) throws SQLException {
		        	Column column = new Column();
		        	column.setSchema(rs.getString("TABLE_SCHEMA"));
		        	column.setTable(rs.getString("TABLE_NAME"));
		        	column.setName(rs.getString("COLUMN_NAME"));
		        	column.setDataType(rs.getString("DATA_TYPE"));
		        	column.setLength(rs.getString("CHARACTER_MAXIMUM_LENGTH"));
				return  column;
		        }
		    });
			
		    // logger.info("# columns size - " + columns.size());
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error("",e1);
			} catch (Exception e) {
				logger.error("",e);
			}
			return columns;
		}
	 
	 public int saveMappingData(List<MappingData> mappingData) {
		 StringBuilder sourceFields = new StringBuilder();
		 StringBuilder targetFields = new StringBuilder();
		 Map<String, String> map = new HashMap<String, String>();
		 int i=0;
		 for(MappingData data : mappingData) {
			 if(i==0) {
				 map.put("sourceSchema", data.getSchema());
				 map.put("sourceTable", data.getTable());
				 map.put("targetSchema", data.getT_schema());
				 map.put("targetTable", data.getT_table());
				 map.put("userId", data.getUserId());
			 }
			 logger.info("sourceFields length  : "+sourceFields.length());
				 if(data.getName()!=null && !data.getName().equalsIgnoreCase("") && sourceFields.length()<=0){
				 sourceFields.append(data.getName());
				 }else if(data.getName()!=null && !data.getName().equalsIgnoreCase("") && sourceFields.length()>0){
				 sourceFields.append(",").append(data.getName());
				 }
				  
				 if(data.getT_name()!=null && !data.getT_name().equalsIgnoreCase("") && targetFields.length()<=0){
				 targetFields.append(data.getT_name());
				 }else if(data.getT_name()!=null && !data.getT_name().equalsIgnoreCase("") && targetFields.length()>=0){
				 targetFields.append(",").append(data.getT_name());	 
				 }
				 
				//}
				 /*
					 * else { if(data.getName()!=null && !data.getName().equalsIgnoreCase("") &&
					 * sourceFields.length()>0){ sourceFields.append(",").append(data.getName()); }
					 * if(data.getT_name()!=null && !data.getT_name().equalsIgnoreCase("") &&
					 * targetFields.length()>0){ targetFields.append(",").append(data.getT_name());
					 * } }
					 */
			 i++;
		 }
		 String sql="INSERT INTO MAPPING_FIELDS (NAME, DESCRIPTION, SOURCE_SCHEMA_NAME, SOURCE_TABLE_NAME, SOURCE_FIELDS, TARGET_SCHEMA_NAME, TARGET_TABLE_NAME, TARGET_FIELDS, CREATED_BY, CREATED, UPDATED_BY, UPDATED) "
		 		+ "VALUES ('"+map.get("sourceSchema")+" TO "+map.get("targetSchema")+"', '"+map.get("sourceSchema")+"."+map.get("sourceTable")+" TO "+map.get("targetSchema")+"."+map.get("targetTable")+"', '"+map.get("sourceSchema")+"', '"+map.get("sourceTable")+"', '"+sourceFields.toString()+"', '"+map.get("targetSchema")+"', '"+map.get("targetTable")+"', '"+targetFields+"', "+map.get("userId")+", CURRENT_TIMESTAMP, "+map.get("userId")+", CURRENT_TIMESTAMP)";
		 logger.info("sql : "+sql);
		//int id = jdbcTemplate3.update(sql);
		//logger.info("id : "+id);
		 KeyHolder keyHolder = new GeneratedKeyHolder();
			/*
			 * jdbcTemplate3.update(new PreparedStatementCreator() { public
			 * PreparedStatement createPreparedStatement(Connection connection) throws
			 * SQLException { return connection.prepareStatement(sql, new String[] {"id"});
			 * } }, keyHolder);
			 */
		 jdbcTemplate3.update(connection -> {
			    PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			    return ps;
			}, keyHolder);
				return keyHolder.getKey().intValue();
	 }

	 public int saveJob(int mappingId,int userId,int totalRows,int totalTime,int pendingRows,int pendingTime) {
		 
		 String sql="INSERT INTO JOBS (MAPPING_ID, NAME, DESCRIPTION, TOTAL_ROWS, TOTAL_TIME, PENDING_ROWS, PENDING_TIME, CREATED_BY, CREATED, UPDATED_BY, UPDATED) "		 		
		 		+ " VALUES ("+mappingId+", null, null, "+totalRows+", "+totalTime+", "+pendingRows+", "+pendingTime+", "+userId+", CURRENT_TIMESTAMP, "+userId+", CURRENT_TIMESTAMP) ";
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
}
