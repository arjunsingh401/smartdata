package com.sdc.controller;

import com.sdc.service.DataTransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DataTransferController {

    private static final Logger logger = LogManager.getLogger(DataTransferController.class);

    @Autowired
    DataTransferService dataTransferService;

    @RequestMapping(value = "/startDataTransfer", method = RequestMethod.POST)
    public String startDataTransfer(String table1, String table2, Map<String, String> fieldMapping) {
        logger.info("Started data transfer with mapping {}", fieldMapping);

        dataTransferService.startDataTransfer(table1, table2, fieldMapping);

        return "Data transfer started successfully";
    }
}
