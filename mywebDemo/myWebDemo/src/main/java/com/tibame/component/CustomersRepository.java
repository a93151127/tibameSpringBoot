package com.tibame.component;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tibame.entity.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers,String> {

	//取出所有客戶資料
	List<Customers> findAll();
	Customers findByCustomerId(String key);
	
	
	

}
