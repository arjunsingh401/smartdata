package com.sdc.controller;

import com.sdc.model.MappingData;
import com.sdc.service.DataTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataTransferController {

    private static final Logger logger = LoggerFactory.getLogger(DataTransferController.class);

    @Autowired
    DataTransferService dataTransferService;

    @RequestMapping(value = "/startDataTransfer", method = RequestMethod.POST)
    public String startDataTransfer(@RequestBody List<MappingData> mappingData) {
        logger.info("Started data transfer with mapping {}", mappingData);

        dataTransferService.startDataTransfer(mappingData);

        return "Data transfer started";
    }
}
