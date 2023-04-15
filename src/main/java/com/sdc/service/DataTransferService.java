package com.sdc.service;

import java.util.Map;

/**
 *
 * Author: Koushik
*/
public interface DataTransferService {
    void startDataTransfer(String table1, String table2, Map<String, String> fieldMapping);
}
