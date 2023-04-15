package com.sdc.serviceImpl;

import com.sdc.service.DataTransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Koushik
 */
@Service
public class DataTransferServiceImpl implements DataTransferService {

    private static final Logger logger = LogManager.getLogger(DataTransferServiceImpl.class);

    @Autowired
    @Qualifier("jdbcTemplate2")
    JdbcTemplate jdbcTemplate2;

    @Autowired
    @Qualifier("jdbcTemplate3")
    JdbcTemplate jdbcTemplate3;

    @Override
    public void startDataTransfer(String table1, String table2, Map<String, String> fieldMapping) {
        logger.info("Select records from table: {}", table1);

        String selectSourceQuery = "select * from " + table1;
        logger.info("Select query: {}", selectSourceQuery);
        
        List<Map<String, Object>> result = jdbcTemplate2.queryForList(selectSourceQuery);
        logger.info("Records found: {}", result.size());

        logger.info("Insert records into table: {}", table2);

        StringBuilder insertQueryBuilder = new StringBuilder();

        StringBuilder insertColumns = new StringBuilder();
        StringBuilder insertValues = new StringBuilder();
        insertQueryBuilder.append("insert into ");
        insertQueryBuilder.append(table2);
        insertQueryBuilder.append(" (");

        Iterator<String> sourceFieldIterator = fieldMapping.keySet().iterator();

        result.forEach(record -> {
            String field = sourceFieldIterator.next();
            insertColumns.append(fieldMapping.get(field));
            insertValues.append(record.get(field));
            while (sourceFieldIterator.hasNext()) {
                field = sourceFieldIterator.next();
                insertColumns.append(", ");
                insertColumns.append(fieldMapping.get(field));

                insertValues.append(", ");
                insertValues.append(record.get(field));
            }
            insertQueryBuilder.append(insertColumns);
            insertQueryBuilder.append(")");
            insertQueryBuilder.append(" values (");
            insertQueryBuilder.append(insertValues);
            insertQueryBuilder.append(")");

            logger.info("Final insert query in table {}", table2);
            logger.info(insertQueryBuilder);

            jdbcTemplate3.update(insertQueryBuilder.toString());
        });
        
    }
}
