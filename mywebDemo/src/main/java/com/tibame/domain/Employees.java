package com.tibame.domain;
//Employees JavaBean
public class Employees implements java.io.Serializable{
	public Employees() {
		this.id=(int)(Math.random()*10000)+1+"";
	}
	private String id;
	private String name;
	private Company company; //員工歸屬一個公司(DI)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
}
