package com.khh.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Integer>{
	//依照訂單編號進行查詢Mapping 採用Native SQL
	@Query(value="Select * From vwOrderDetailsProducts Where OrderID=:orderid",nativeQuery=true)
	public List<OrderDetails> detailsQry(@Param("orderid")Integer orderId);
}
