/**
 * 
 */
package com.sdc.model;

import org.springframework.stereotype.Component;

/**
 * @author arjun
 *
 */
public class Column {

	private String schema;
	private String table;
	private String name;
	private String dataType;
	private String length;
	public Column() {
		super();
	}
	public Column(String schema, String table, String name, String dataType, String length) {
		super();
		this.schema = schema;
		this.table = table;
		this.name = name;
		this.dataType = dataType;
		this.length = length;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
	
}
