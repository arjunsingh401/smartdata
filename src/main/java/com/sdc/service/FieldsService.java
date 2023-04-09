/**
 * 
 */
package com.sdc.service;

import java.util.List;

import com.sdc.model.Schema;

/**
 * @author arjun
 *
 */
public interface FieldsService {

	List<Schema> getSourceFields(String source);
	List<Schema> getTargetFields(String source);

}
