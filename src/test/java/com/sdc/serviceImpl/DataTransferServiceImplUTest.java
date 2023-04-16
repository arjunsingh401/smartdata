package com.sdc.serviceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class DataTransferServiceImplUTest {

    @Mock
    private JdbcTemplate jdbcTemplate2;

    @Mock
    private JdbcTemplate jdbcTemplate3;

    @InjectMocks
    DataTransferServiceImpl service;

    //@Test
    public void test_dataTransfer() {
        Map<String, Object> record = new HashMap<>();
        record.put("account_number", 12345);
        record.put("account_holder", "koushik");
        record.put("account_type", "saving");
        record.put("account_address", "pune");

        List<Map<String, Object>> result = new ArrayList<>();
        result.add(record);

        ReflectionTestUtils.setField(service, "jdbcTemplate2", jdbcTemplate2);
        ReflectionTestUtils.setField(service, "jdbcTemplate3", jdbcTemplate3);

        Mockito.when(jdbcTemplate2.queryForList(Mockito.anyString())).thenReturn(result);
        Mockito.when(jdbcTemplate3.update(Mockito.anyString())).thenReturn(1);

        Map<String, String> fieldMapping = new HashMap<>();
        fieldMapping.put("account_number", "account_id");
        fieldMapping.put("account_holder", "name");
        fieldMapping.put("account_type", "type");
        fieldMapping.put("account_address", "address");

        //service.startDataTransfer("table1", "table2", fieldMapping);

    }
}
