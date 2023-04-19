/**
 * 
 */
package com.sdc.model;

/**
 * @author arjun
 *
 */
public class DbConnection {

	private int id;
	private String type;
	private String name;
	private String description;
	private String url;
	private String driverclass;
	private String username;
	private String password;
	private String userId;
	public DbConnection() {
		super();
	}
	public DbConnection(int id, String type, String name, String description, String url, String driverclass,
			String username, String password) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.description = description;
		this.url = url;
		this.driverclass = driverclass;
		this.username = username;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDriverclass() {
		return driverclass;
	}
	public void setDriverclass(String driverclass) {
		this.driverclass = driverclass;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
