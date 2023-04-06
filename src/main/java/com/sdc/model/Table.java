/**
 * 
 */
package com.sdc.model;

import java.util.List;

/**
 * @author arjun
 *
 */
public class Table {

	private String schema;
	private String name;
	private List<Column> columns;
	
	public Table() {
		super();
	}
	
	public Table(String schema, String name, List<Column> columns) {
		super();
		this.schema = schema;
		this.name = name;
		this.columns = columns;
	}

	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
}
