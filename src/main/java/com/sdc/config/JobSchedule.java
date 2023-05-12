package com.sdc.config;

import com.sdc.repository.JobsRepository;
import com.sdc.service.DataTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class JobSchedule {
    private static final Logger logger = LoggerFactory.getLogger("console");

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    DataTransferService dataTransferService;

    @Scheduled(fixedRate = 30 * 1000)
    public void runJobSchedule() {
        logger.info("Started job schedule");

        try {
            List<Map<String, Object>> result = jobsRepository.getRunningJobsByScheduleOrder();

            if (result.size() == 0) {
                result = jobsRepository.getCreatedJobsByScheduleOrder();
                if (result.size() > 0) {
                    Map<String, Object> data = result.get(0);
                    Integer jobId = (Integer) data.get("ID");
                    dataTransferService.startDataTransfer(jobId);
                }
            }

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
    }
}
