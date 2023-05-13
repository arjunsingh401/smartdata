package com.sdc.service;

import com.sdc.model.MappingData;

import java.util.List;

/**
 *
 * Author: Koushik
*/
public interface DataTransferService {
    void startDataTransfer(Integer jobId) throws InterruptedException;

    void saveDataTransferJob(List<MappingData> mappingData);

    void scheduleJob(String jobId);
}
