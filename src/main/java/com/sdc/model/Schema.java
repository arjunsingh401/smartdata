/**
 * 
 */
package com.sdc.model;

import java.util.List;

/**
 * @author arjun
 *
 */
public class Schema {

	private String schemaName;
	private List<Table> tables;

	
	public Schema() {
		super();
	}

	
	public Schema(String schemaName, List<Table> tables) {
		super();
		this.schemaName = schemaName;
		this.tables = tables;
	}


	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}


	public List<Table> getTables() {
		return tables;
	}


	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
	
}
