package com.sdc.model;

import java.io.Serializable;

/**
 * @author User
 *
 */
/**
 * @author User
 *
 */
public class MappingData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String schema;
	private String table;
	private String name;
	private String dataType;
	private String length;
	
	private String t_schema;
	private String t_table;
	private String t_name;
	private String t_dataType;
	private String t_length;
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
	public String getT_schema() {
		return t_schema;
	}
	public void setT_schema(String t_schema) {
		this.t_schema = t_schema;
	}
	public String getT_table() {
		return t_table;
	}
	public void setT_table(String t_table) {
		this.t_table = t_table;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getT_dataType() {
		return t_dataType;
	}
	public void setT_dataType(String t_dataType) {
		this.t_dataType = t_dataType;
	}
	public String getT_length() {
		return t_length;
	}
	public void setT_length(String t_length) {
		this.t_length = t_length;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}