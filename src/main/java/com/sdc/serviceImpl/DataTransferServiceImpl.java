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
import java.util.*;

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
    public void startDataTransfer(String jobId) throws InterruptedException {

        Map<String, Object> job = fieldsRepository.getJob(Integer.parseInt(jobId));
        int mappingId = (int) job.get("MAPPING_ID");
        logger.info("mappingId : " + mappingId);

        try {

            List<MappingData> mappingData = fieldsRepository.getMappingDetails(mappingId);

            String table1 = mappingData.get(0).getTable();
            String table2 = mappingData.get(0).getT_table();
            logger.info("Select records from table: {}", table1);

            jdbcTemplateSource = createJdbcTemplate(mappingData.get(0).getDatabase());
            jdbcTemplateDestination = createJdbcTemplate(mappingData.get(0).getT_database());

            InsertDataThread insertDataThread = new InsertDataThread(Integer.parseInt(jobId), mappingData, fieldsRepository,
                    jdbcTemplateSource, jdbcTemplateDestination, batchSize, table1 + "-to-" + table2);

            insertDataThread.start();
            insertDataThread.join();

            logger.info("Data transfer thread started");
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getMessage());
        }
    }

    @Override
    public void saveDataTransferJob(List<MappingData> mappingData) {
        logger.debug("Storing job details with given mapping: {}", mappingData);

        int mappingId = fieldsRepository.saveMappingData(mappingData);
        logger.info("mappingId : " + mappingId);

        String table1 = mappingData.get(0).getTable();
        String table2 = mappingData.get(0).getT_table();

        jdbcTemplateSource = createJdbcTemplate(mappingData.get(0).getDatabase());
        int records = fieldsRepository.getNumberOfRecords(table1, jdbcTemplateSource);

        fieldsRepository.saveJob(mappingId, table1 + "-to-" + table2, table1 + "-to-" + table2, Integer.parseInt(mappingData.get(0).getUserId()),
                records, 0, 0, 0);
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

}
