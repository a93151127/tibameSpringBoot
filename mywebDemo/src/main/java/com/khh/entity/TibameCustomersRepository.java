package com.khh.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//客戶資料Repository
@Repository
public interface TibameCustomersRepository extends JpaRepository<Customer,String> {
	//查詢客戶所有資料
	List<Customer> findAll();
	
	//依照客戶編號找
	Customer findByCustomerId(String customerid);
	List<Customer> findByCountry(String country);
	//刪除特定的Customer物件 同步刪除資料表相對的記錄
	void delete(Customer customer);
	//Native SQL查詢
	@Query(value="Select CustomerID,CompanyName,Address,Phone,Country,City From Customers",nativeQuery=true)
	List<Customer> queryAll();
	
}
