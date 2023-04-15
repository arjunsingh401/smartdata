package com.sdc.controller;

import com.sdc.model.MappingData;
import com.sdc.service.DataTransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DataTransferController {

    private static final Logger logger = LogManager.getLogger(DataTransferController.class);

    @Autowired
    DataTransferService dataTransferService;

    @RequestMapping(value = "/startDataTransfer", method = RequestMethod.POST)
    public String startDataTransfer(@RequestBody List<MappingData> mappingData) {
        logger.info("Started data transfer with mapping {}", mappingData);

        dataTransferService.startDataTransfer(mappingData);

        return "Data transfer started successfully";
    }
}
