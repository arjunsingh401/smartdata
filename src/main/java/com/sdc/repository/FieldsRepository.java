/**
 * 
 */
package com.sdc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sdc.model.Column;
import com.sdc.model.Schema;
import com.sdc.model.Table;

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
	 
	 public List<Schema> getSourceFields(String source) {
		 	String sql ="SELECT name AS schema_name,  schema_id FROM sys.schemas ORDER BY name ";
			
			logger.info("schema sql :"+sql);
			List<Schema> schemas = new ArrayList<Schema>(); 
			try {
				schemas = jdbcTemplate1.query(sql,new RowMapper<Schema>() {
		        @Override
		        public Schema mapRow(ResultSet rs, int i) throws SQLException {
		        	Schema schema = new Schema();
		        	schema.setSchemaName(rs.getString("schema_name"));
		        	schema.setTables(getTables(schema.getSchemaName()));
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
	 
	 public List<Table> getTables(String schema) {
		 	String sql ="SELECT * FROM information_schema.tables WHERE TABLE_TYPE='BASE TABLE' and TABLE_SCHEMA = '"+schema+"'";
			
			logger.info("table sql :"+sql);
			List<Table> tables = new ArrayList<Table>(); 
			try {
				tables = jdbcTemplate1.query(sql,new RowMapper<Table>() {
		        @Override
		        public Table mapRow(ResultSet rs, int i) throws SQLException {
		        	Table table = new Table();
		        	table.setSchema(rs.getString("TABLE_SCHEMA"));
		        	table.setName(rs.getString("TABLE_NAME"));
		        	table.setColumns(getColumns(table.getName()));
		        	
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
	 
	 public List<Column> getColumns(String table) {
		 	String sql ="SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE  TABLE_NAME = '"+table+"'";
			
			logger.info("column sql :"+sql);
			List<Column> columns = new ArrayList<Column>(); 
			try {
				columns = jdbcTemplate1.query(sql,new RowMapper<Column>() {
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
			
		     logger.info("# columns size - " + columns.size());
			} catch(EmptyResultDataAccessException e1) {
				e1.printStackTrace();
				logger.error("",e1);
			} catch (Exception e) {
				logger.error("",e);
			}
			return columns;
		}
	 
}
