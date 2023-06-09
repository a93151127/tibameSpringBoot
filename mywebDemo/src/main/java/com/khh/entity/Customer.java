package com.khh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//JavaBean 變成ORM Entity Class
@Entity
@Table(name="Customers")
public class Customer implements java.io.Serializable{
	//Attribute(Data Field) 實現封裝性
	@Id
	@Column(name="Customerid",length=5,nullable=false)
	private String customerId;
	@Column(name="Companyname",length=40,nullable=false)
	private String companyName;
	@Column(name="Address",length=60,nullable=true)
	private String address;
	@Column(name="Phone",length=24,nullable=true)
	private String phone;
	@Column(name="Country",length=15,nullable=true)
	private String country;
	//setter and getter 去頭 customerId Property
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	

}
