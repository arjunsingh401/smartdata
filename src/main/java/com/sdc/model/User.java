/**
 * 
 */
package com.sdc.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * @author arjun
 *
 */
@Component
public class User implements Serializable {
	
	
	private int userId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String middleName;
	private String companyName;
	private int vendorId;
	private String vendorExpDate;
	private String active;
	private String emailId;
	private String phone;
	private String displayErrorMsg;
	
	public User() {
		super();
	}
	
	public User(int userId, String userName, String password, String firstName, String lastName, String middleName,
			String companyName, int vendorId, String vendorExpDate, String active, String emailId, String phone,
			String displayErrorMsg) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.companyName = companyName;
		this.vendorId = vendorId;
		this.vendorExpDate = vendorExpDate;
		this.active = active;
		this.emailId = emailId;
		this.phone = phone;
		this.displayErrorMsg = displayErrorMsg;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorExpDate() {
		return vendorExpDate;
	}
	public void setVendorExpDate(String vendorExpDate) {
		this.vendorExpDate = vendorExpDate;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDisplayErrorMsg() {
		return displayErrorMsg;
	}

	public void setDisplayErrorMsg(String displayErrorMsg) {
		this.displayErrorMsg = displayErrorMsg;
	}

	
	
}
