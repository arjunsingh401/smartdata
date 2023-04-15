package com.sdc.service;

import com.sdc.model.MappingData;

import java.util.List;
import java.util.Map;

/**
 *
 * Author: Koushik
*/
public interface DataTransferService {
    void startDataTransfer(List<MappingData> mappingData);
}
