package com.khh.entity;

import java.util.Objects;

import javax.persistence.Embeddable;
//複合鍵(Id) 欄位類別
@Embeddable
public class OrderIdProductID  implements java.io.Serializable{
	private int orderid;
	private int productid;
	
	
	@Override
	public int hashCode() {
		return Objects.hash(orderid, productid);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderIdProductID other = (OrderIdProductID) obj;
		return orderid == other.orderid && productid == other.productid;
	}
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
	

}
