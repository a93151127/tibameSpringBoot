package com.tibame.domain;
//汽車引擎JavaBean
public class Engine {
	private String type;
	private int cylinder; //汽缸數
	public Engine(String type,int cylinder)
	{
		this.type=type;
		this.cylinder=cylinder;
	}
	public String getType() {
		return type;
	}
	public int getCylinder() {
		return cylinder;
	}
	public void setType(String type) {
		this.type = type;
	}

}
