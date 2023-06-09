package com.tibame.domain;
//JavaBean  空參數建構子 具有封裝資訊的attribute
//具有setter and getter
public class MemberEntity implements java.io.Serializable{
	//Attribute
	private String userName;
	private String password;
	private String email;
	public String getUserName() {
		return userName;
	}
	//表單欄位 name=userName 透過Property setXxx() getXxx() 去頭 xxx=value
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
