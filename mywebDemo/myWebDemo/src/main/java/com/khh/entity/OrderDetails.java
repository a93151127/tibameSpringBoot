package com.khh.entity;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

//描述應對資料庫View vwOrderDetailsProducts輸出欄位
@Entity
@IdClass(OrderIdProductID.class)
public class OrderDetails implements java.io.Serializable {
	//Attribute  將orderid and productid 進行複合鍵
	@Id
	private int orderid;
	@Id
	private int productid;
	private String productname;
	private double unitprice;
	private short quantity;
	private double totalamt;
	
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	public double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}
	public short getQuantity() {
		return quantity;
	}
	public void setQuantity(short quantity) {
		this.quantity = quantity;
	}
	public double getTotalamt() {
		return totalamt;
	}
	public void setTotalamt(double totalamt) {
		this.totalamt = totalamt;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("產品編號:%d 名稱:%s",productid,productname);
	}
	
	

}
