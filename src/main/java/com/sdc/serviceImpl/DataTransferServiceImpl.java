package com.sdc.serviceImpl;

import com.sdc.model.DbConnection;
import com.sdc.model.MappingData;
import com.sdc.repository.DBConnectionRepository;
import com.sdc.repository.FieldsRepository;
import com.sdc.service.DataTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
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

    private static final Logger logger = LoggerFactory.getLogger("console");

    JdbcTemplate jdbcTemplateSource;

    JdbcTemplate jdbcTemplateDestination;

    @Autowired
    FieldsRepository fieldsRepository;

    @Autowired
    DBConnectionRepository dbConnectionRepository;
    
    @Value("${database.insert.batch.size}")
    int batchSize;

    @Override
    public void startDataTransfer(List<MappingData> mappingData) throws InterruptedException {
        int mappingId = fieldsRepository.saveMappingData(mappingData);
        logger.info("mappingId : " + mappingId);
        String table1 = mappingData.get(0).getTable();
        String table2 = mappingData.get(0).getT_table();
        logger.info("Select records from table: {}", table1);

        jdbcTemplateSource = createJdbcTemplate(mappingData.get(0).getDatabase());
        jdbcTemplateDestination = createJdbcTemplate(mappingData.get(0).getT_database());

        int records = fieldsRepository.getNumberOfRecords(table1, jdbcTemplateSource);

        int jobId = fieldsRepository.saveJob(mappingId, table1 + "-to-" + table2, table1 + "-to-" + table2, Integer.parseInt(mappingData.get(0).getUserId()),
                records, 0, 0, 0);

        InsertDataThread insertDataThread = new InsertDataThread(jobId, mappingData, fieldsRepository,
                jdbcTemplateSource, jdbcTemplateDestination, batchSize, table1 + "-to-" + table2);

        insertDataThread.start();
        insertDataThread.join();

        logger.info("Data transfer thread started");
    }

    private JdbcTemplate createJdbcTemplate(String database) {
        DataSource dataSource = getDataSource(database);
        return new JdbcTemplate(dataSource);
    }

    private DataSource getDataSource(String database) {
        DbConnection dbConnection = dbConnectionRepository.getDbConnectionByName(database);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbConnection.getDriverclass());
        dataSource.setUrl(dbConnection.getUrl());
        dataSource.setUsername(dbConnection.getUsername());
        dataSource.setPassword(dbConnection.getPassword());
        return dataSource;
    }

    private void setPreparedStatement(PreparedStatement ps, Map<String, Object> argument) throws SQLException {
        Set<String> columns = argument.keySet();
        int index = 1;

        for(String col: columns) {
        	logger.info("index : "+index+" col : "+col+" value : "+argument.get(col));
            ps.setObject(index, argument.get(col));
            index++;
        }
    }

    private String getSelectQuery(List<MappingData> mappingData) {
        
    	String table = mappingData.get(0).getTable();
        
        StringBuilder sb = new StringBuilder("select ");

        for (MappingData map : mappingData) {
            if (map.getName() != null && !map.getName().equalsIgnoreCase("")) {
                sb.append(map.getName()).append(",");
            }
        }
        sb.deleteCharAt(sb.length()-1);  

        sb.append(" from ").append(table);

        return sb.toString();
    }

    private String getInsertQuery(List<MappingData> mappingData) {
        String table = mappingData.get(0).getT_table();

        StringBuilder queryBuilder = new StringBuilder("insert into ").append(table);

        Iterator<MappingData> itr = mappingData.iterator();
        MappingData data = itr.next();
        StringBuilder columnBuilder = new StringBuilder(" (");

        StringBuilder parameterBuilder = new StringBuilder(" values (");

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
