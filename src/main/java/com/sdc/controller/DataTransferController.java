package com.sdc.controller;

import com.sdc.model.MappingData;
import com.sdc.service.DataTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataTransferController {

    private static final Logger logger = LoggerFactory.getLogger("console");

    @Autowired
    DataTransferService dataTransferService;

    @RequestMapping(value = "/saveDataTransferJob", method = RequestMethod.POST)
    public String saveDataTransferJob(@RequestBody List<MappingData> mappingData) throws InterruptedException {
        logger.info("Started data transfer with mapping {}", mappingData);

        dataTransferService.saveDataTransferJob(mappingData);

        return "Job saved successfully";
    }
}
