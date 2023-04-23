/**
 * 
 */
package com.sdc.model;

/**
 * @author arjun
 *
 */
public class Jobs {

	
	private int id;
	private String name;
	private String description;
	private String totalRows;
	private String totalTime;
	private String pendingRows;
	private String pendingTime;
	private String sourceDbName;
	private String targetDbName;
	private String createdBy;
	public Jobs() {
		super();
	}
	public Jobs(int id, String name, String description, String totalRows, String totalTime, String pendingRows,
			String pendingTime, String sourceDbName, String targetDbName, String createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.totalRows = totalRows;
		this.totalTime = totalTime;
		this.pendingRows = pendingRows;
		this.pendingTime = pendingTime;
		this.sourceDbName = sourceDbName;
		this.targetDbName = targetDbName;
		this.createdBy = createdBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(String totalRows) {
		this.totalRows = totalRows;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getPendingRows() {
		return pendingRows;
	}
	public void setPendingRows(String pendingRows) {
		this.pendingRows = pendingRows;
	}
	public String getPendingTime() {
		return pendingTime;
	}
	public void setPendingTime(String pendingTime) {
		this.pendingTime = pendingTime;
	}
	public String getSourceDbName() {
		return sourceDbName;
	}
	public void setSourceDbName(String sourceDbName) {
		this.sourceDbName = sourceDbName;
	}
	public String getTargetDbName() {
		return targetDbName;
	}
	public void setTargetDbName(String targetDbName) {
		this.targetDbName = targetDbName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
