package com.sdc.serviceImpl;

import com.sdc.BatchStatus;
import com.sdc.model.MappingData;
import com.sdc.repository.FieldsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InsertDataThread extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(InsertDataThread.class);
    private static final Logger fileLogger = LoggerFactory.getLogger("file");

    JdbcTemplate jdbcTemplateSource;

    JdbcTemplate jdbcTemplateDestination;

    FieldsRepository fieldsRepository;

    int batchSize;

    List<MappingData> mappingData;

    int jobId;

    String threadName;

    public InsertDataThread(int jobId, List<MappingData> mappingData, FieldsRepository fieldsRepository,
                            JdbcTemplate jdbcTemplateSource,
                            JdbcTemplate jdbcTemplateDestination, int batchSize, String threadName) {
        this.jobId = jobId;
        this.mappingData = mappingData;
        this.fieldsRepository = fieldsRepository;
        this.jdbcTemplateSource = jdbcTemplateSource;
        this.jdbcTemplateDestination = jdbcTemplateDestination;
        this.batchSize = batchSize;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        currentThread().setName(threadName);

        String table1 = mappingData.get(0).getTable();
        logger.info("Select records from table: {}", table1);

        String selectSourceQuery = getSelectQuery(mappingData);
        //logger.info("Select query: {}", selectSourceQuery);

        Map<String, Object> job = fieldsRepository.getJob(jobId);

        BigDecimal totalRecords = (BigDecimal) job.get("TOTAL_ROWS");
        long numberOfBatches = (totalRecords.longValue()/batchSize) + (totalRecords.longValue() % batchSize);
        int pendingRecords = totalRecords.intValue();

        BigDecimal failedRecords = (BigDecimal) job.get("FAILED_RECORDS");
        long totalFailed = failedRecords.longValue();

        String selectOffsetQuery;
        String status;
        List<Map<String, Object>> result = null;
        for (int i=0; i<numberOfBatches; i++) {
            try {
                status = fieldsRepository.getJobStatus(jobId);
                if (BatchStatus.TERMINATED.toString().equalsIgnoreCase(status)) {
                    return;
                }

                jdbcTemplateSource.setMaxRows(batchSize);
                int offset = batchSize * i;
                int rows = batchSize;
                selectOffsetQuery = selectSourceQuery
                        .replace("<offset>", String.valueOf(offset)).replace("<rows>", String.valueOf(rows));
                logger.info("Select query: {}", selectOffsetQuery);

                result = jdbcTemplateSource.queryForList(selectOffsetQuery);
                logger.info("Records found: {}", result.size());

                String table2 = mappingData.get(0).getT_table();

                logger.info("Insert records into table: {}", table2);

                int[][] updateCounts = jdbcTemplateDestination.batchUpdate(getInsertQuery(mappingData), result, batchSize, new ParameterizedPreparedStatementSetter<Map<String,Object>>() {
                    public void setValues(PreparedStatement ps, Map<String, Object>argument) throws SQLException {
                        setPreparedStatement(ps, argument);
                    }
                });

                //add failure logs with error message
                logger.info("Records processed: {}/{}", (batchSize * (i + 1)), totalRecords);
                fileLogger.info("Records processed: {}/{}", (batchSize * (i + 1)), totalRecords);
                fieldsRepository.updateJob(jobId, (pendingRecords -= result.size()), 0, BatchStatus.RUNNING.toString());
            } catch (Exception e) {
                fileLogger.error("Error inserting data: {}", e.getMessage());
                logger.error("Exception occurred: {}", e.getMessage());
                totalFailed += result.size();
                fieldsRepository.updateFailedRecords(jobId, totalFailed);
            }
        }
        fieldsRepository.updateJob(jobId, 0, 0, BatchStatus.COMPLETED.toString());
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
        sb.append(" order by ").append(mappingData.get(0).getName());
        sb.append(" OFFSET <offset> ROWS ");
        sb.append(" FETCH NEXT <rows> ROWS ONLY");

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
