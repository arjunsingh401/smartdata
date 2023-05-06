package com.sdc.service;

import com.sdc.model.MappingData;

import java.util.List;

/**
 *
 * Author: Koushik
*/
public interface DataTransferService {
    void startDataTransfer(String mappingData) throws InterruptedException;

    void saveDataTransferJob(List<MappingData> mappingData);
}
