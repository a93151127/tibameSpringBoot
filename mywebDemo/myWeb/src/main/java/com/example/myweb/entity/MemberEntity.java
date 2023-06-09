package com.example.myweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Member") //應對資料表名稱
public class MemberEntity {
	//Data Field
	@Id
	@Column(name="username",nullable=false)
	private String userName;
	@Column(name="password",nullable=false)
	private String password;
	@Column(name="email",nullable=false)
	private String email;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
