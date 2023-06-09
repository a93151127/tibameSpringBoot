package com.tibame.entity;

import java.util.ArrayList;
import java.util.List;

//公司行號類別JavaBean
public class Company implements java.io.Serializable {
	private String companyName;
	private String address;
	private String phone;
	private List<Employees> employees=new ArrayList<>();
	
	public List<Employees> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employees> employees) {
		this.employees = employees;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

}
