package com.khh.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomersCRUDRepository extends JpaRepository<Customer,String>{
	//新增修改
	public Customer save(Customer customer);
	//新增客戶資料
	@Modifying(clearAutomatically = true)
	@Transactional //啟動交易處理 如果使用@Modifying 執行新增修改或者刪除 必須ON Transaction
	@Query(value="Insert Into Customers(CustomerID,CompanyName,Address,Phone,Country) values(:#{#cust.customerId},:#{#cust.companyName},:#{#cust.address},:#{#cust.phone},:#{#cust.country})",nativeQuery=true)
	public int insert(@Param("cust")Customer customer);
	
	//刪除 依照國家別
	@Modifying(clearAutomatically = true) //Persistence Entity row State(異動狀態碼)
	@Transactional //ACID Transaction對應
	@Query(value="Delete From Customers Where Country=:country",nativeQuery=true)
	public int deleteForCountry(@Param("country")String country);

}
