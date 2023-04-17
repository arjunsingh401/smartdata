package com.sdc.serviceImpl;

import com.sdc.model.MappingData;
import com.sdc.repository.FieldsRepository;
import com.sdc.service.DataTransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Author: Koushik
 */
@Service
public class DataTransferServiceImpl implements DataTransferService {

    private static final Logger logger = LogManager.getLogger(DataTransferServiceImpl.class);

    @Autowired
    @Qualifier("jdbcTemplate1")
    JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("jdbcTemplate2")
    JdbcTemplate jdbcTemplate2;

    @Autowired
    FieldsRepository fieldsRepository;
    
    @Value("${database.insert.batch.size}")
    int batchSize;

    
    
    @Override
    public void startDataTransfer(List<MappingData> mappingData) {
        
    	int mappingId = fieldsRepository.saveMappingData(mappingData);
    	logger.info("mappingId : "+mappingId);
    	
		  String table1 = mappingData.get(0).getTable();
		  logger.info("Select records from table: {}", table1);
		  
		  String selectSourceQuery = getSelectQuery(mappingData);
		  logger.info("Select query: {}", selectSourceQuery);
		  
		  List<Map<String, Object>> result = jdbcTemplate1.queryForList(selectSourceQuery);
		  logger.info("Records found: {}", result.size());
		  int jobId = fieldsRepository.saveJob(mappingId,Integer.parseInt(mappingData.get(0).getUserId()), result.size(), 0, 0, 0);
		  String table2 = mappingData.get(0).getT_table();
		  
		  logger.info("Insert records into table: {}", table2);
		  
		  int[][] updateCounts = jdbcTemplate2.batchUpdate(getInsertQuery(mappingData),
		  result, batchSize, new ParameterizedPreparedStatementSetter<Map<String,
		  Object>>() { public void setValues(PreparedStatement ps, Map<String, Object>
		  argument) throws SQLException { setPreparedStatement(ps, argument); } });
		  
		  logger.info("Inserted records: {}", updateCounts.length);
		 
        
    }

    private void setPreparedStatement(PreparedStatement ps, Map<String, Object> argument) throws SQLException {
        Set<String> columns = argument.keySet();
        int index = 1;
		/*
		 logger.info("columns : "+columns);
		 logger.info("columns size : "+columns.size());
		 logger.info("itr length : "+itr.next().length());
		 */
        for(String col: columns) {
        	logger.info("index : "+index+" col : "+col+" value : "+argument.get(col));
            ps.setObject(index, argument.get(col));
            index++;
        }
		/*
		 * while (itr.hasNext()) {
		 * logger.info("index : "+index+" itr next : "+itr.next()+" argument : "
		 * +argument.get(itr.next())); ps.setObject(index, argument.get(itr.next()));
		 * index++; }
		 */
    }

    private String getSelectQuery(List<MappingData> mappingData) {
        
    	
    	String table = mappingData.get(0).getTable();
        
        StringBuilder sb = new StringBuilder("select ");

      //  Iterator<MappingData> itr = mappingData.iterator();
        //MappingData data = itr.next();
        //sb.append(data.getName());

        for(MappingData map : mappingData) {
        	if(map.getName()!=null && !map.getName().equalsIgnoreCase("")){
        	sb.append(map.getName()).append(",");
        	}
        }
        sb.deleteCharAt(sb.length()-1);  

		/*
		 * while (itr.hasNext()) { data = itr.next();
		 * 
		 * sb.append(", ").append(data.getName()); }
		 */

        sb.append(" from ").append(table);

        return sb.toString();
    }

    private String getInsertQuery(List<MappingData> mappingData) {
        String table = mappingData.get(0).getT_table();

        StringBuilder queryBuilder = new StringBuilder("insert into ").append(table);

        Iterator<MappingData> itr = mappingData.iterator();
        MappingData data = itr.next();
        StringBuilder columnBuilder = new StringBuilder(" (");
       // columnBuilder.append(data.getT_name());

        StringBuilder parameterBuilder = new StringBuilder(" values (");
		/*
		 * parameterBuilder.append("?");
		 * 
		 * while (itr.hasNext()) { data = itr.next();
		 * columnBuilder.append(", ").append(data.getT_name());
		 * parameterBuilder.append(",?"); }
		 */

        for(MappingData map : mappingData) {
        	if(map.getName()!=null && !map.getName().equalsIgnoreCase("")){
        		columnBuilder.append(map.getT_name()).append(",");
        		parameterBuilder.append("?,");
        	}
        }
        columnBuilder.deleteCharAt(columnBuilder.length()-1);
        parameterBuilder.deleteCharAt(parameterBuilder.length()-1);
        
        columnBuilder.append(")");
        parameterBuilder.append(")");

        queryBuilder.append(columnBuilder).append(parameterBuilder);

        logger.info("Insert query: {}", queryBuilder);

        return  queryBuilder.toString();
    }
}
