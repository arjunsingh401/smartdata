package com.sdc.serviceImpl;

import com.sdc.BatchStatus;
import com.sdc.model.MappingData;
import com.sdc.repository.FieldsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class InsertDataThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger("console");
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
        Calendar cal = Calendar.getInstance();
        threadName = threadName + "_" + new SimpleDateFormat("yyyyMMddhhmmss").format(cal.getTime());
        currentThread().setName(threadName);
        MDC.put("logFileName", threadName);

        String table1 = mappingData.get(0).getTable();
        logger.info("Select records from table: {}", table1);

        String selectSourceQuery = getSelectQuery(mappingData);

        Map<String, Object> job = fieldsRepository.getJob(jobId);

        BigDecimal totalRecords = (BigDecimal) job.get("TOTAL_ROWS");
        // long numberOfBatches = (totalRecords.longValue()/batchSize) + (totalRecords.longValue() % batchSize);
        long numberOfBatches = (totalRecords.longValue() / batchSize) + 1;
        int pendingRecords = totalRecords.intValue();

        BigDecimal failedRecords = (BigDecimal) job.get("FAILED_RECORDS");
        long totalFailed = failedRecords.longValue();
        //long currentFailed = 0l;

        String selectOffsetQuery;
        String status = BatchStatus.COMPLETED.toString();
        List<Map<String, Object>> result = null;
        for (int i = 0; i < numberOfBatches; i++) {
            try {
                status = fieldsRepository.getJobStatus(jobId);
                if (BatchStatus.TERMINATED.toString().equalsIgnoreCase(status)) {
                    status = BatchStatus.TERMINATED.toString();
                    break;
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

                pendingRecords -= result.size();

                fieldsRepository.updateJob(jobId, pendingRecords, 0, BatchStatus.RUNNING.toString());

                logger.info("Insert records into table: {}", table2);

                int[][] updateCounts = jdbcTemplateDestination.batchUpdate(getInsertQuery(mappingData), result, batchSize, new ParameterizedPreparedStatementSetter<Map<String, Object>>() {
                    public void setValues(PreparedStatement ps, Map<String, Object> argument) throws SQLException {
                        setPreparedStatement(ps, argument);
                    }
                });

                //add failure logs with error message
                logger.info("Records processed: {}/{}", (batchSize * (i + 1)), totalRecords);

            } catch (Exception e) {
                fieldsRepository.updateErrorFileName(jobId, threadName);

                int[] updateCounts = ((BatchUpdateException) e.getCause()).getUpdateCounts();
                int currentFailed = 0;
                logger.error("updateCounts : " + updateCounts);

                List<Integer> updateCountsList = Arrays.stream(updateCounts).boxed().collect(Collectors.toList());

                currentFailed = Collections.frequency(updateCountsList, Statement.EXECUTE_FAILED);

                totalFailed += currentFailed;
                logger.error("currentFailed : " + currentFailed);
                fieldsRepository.updateFailedRecords(jobId, totalFailed);

                if (e.getCause() instanceof BatchUpdateException) {
                    BatchUpdateException be = (BatchUpdateException) e.getCause();
                    int[] batchRes = be.getUpdateCounts();
                    if (batchRes != null && batchRes.length > 0) {
                        for (int index = 0; index < batchRes.length; index++) {
                            if (batchRes[index] == Statement.EXECUTE_FAILED) {
                                fileLogger.error("Error execution: {}, codeFail: {}, line: {}", index, batchRes[index], result.get(index));
                            }
                        }
                    }
                }

                //fileLogger.error("Error inserting data: {}", e.getMessage());

                logger.error("Exception occurred: {}", e.getMessage());
            }
        }
        if (BatchStatus.TERMINATED.toString().equalsIgnoreCase(status)) {
            fieldsRepository.updateJob(jobId, pendingRecords, 0, status);
        } else if (BatchStatus.RUNNING.toString().equalsIgnoreCase(status)) {
            fieldsRepository.updateJob(jobId, pendingRecords, 0, BatchStatus.COMPLETED.toString());
        }
    }

    private long insertRecords(JdbcTemplate jdbcTemplateDestination, List<Map<String, Object>> result) {
        long failed = 0l;
        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> argument = result.get(i);
            try {
                jdbcTemplateDestination.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(getInsertQuery(mappingData));
                    return setPreparedStatement(ps, argument);
                });
            } catch (Exception e) {
                failed += 1l;
                fileLogger.error("Error: " + e.getMessage());
            }
        }
        return failed;
    }

    private PreparedStatement setPreparedStatement(PreparedStatement ps, Map<String, Object> argument) throws SQLException {
        Set<String> columns = argument.keySet();
        int index = 1;

        for (String col : columns) {
            logger.info("index : " + index + " col : " + col + " value : " + argument.get(col));
            ps.setObject(index, argument.get(col));
            index++;
        }
        return ps;
    }

    private String getSelectQuery(List<MappingData> mappingData) {

        String table = mappingData.get(0).getTable();

        StringBuilder sb = new StringBuilder("select ");

        for (MappingData map : mappingData) {
            if (map.getName() != null && !map.getName().equalsIgnoreCase("")) {
                sb.append(map.getName()).append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);

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

        for (MappingData map : mappingData) {
            if (map.getName() != null && !map.getName().equalsIgnoreCase("")) {
                columnBuilder.append(map.getT_name()).append(",");
                parameterBuilder.append("?,");
            }
        }
        columnBuilder.deleteCharAt(columnBuilder.length() - 1);
        parameterBuilder.deleteCharAt(parameterBuilder.length() - 1);

        columnBuilder.append(")");
        parameterBuilder.append(")");

        queryBuilder.append(columnBuilder).append(parameterBuilder);

        logger.info("Insert query: {}", queryBuilder);

        return queryBuilder.toString();
    }
}
