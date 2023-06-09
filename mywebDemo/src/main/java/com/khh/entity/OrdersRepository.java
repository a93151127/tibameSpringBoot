package com.khh.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//Generic(T,Key) T自訂Entity class影響參數會者是回應值
//Entity class為了@Query設定Native SQL法語配合結果規劃的 (不一定要Mapping 資料庫Table)
@Repository
public interface OrdersRepository extends JpaRepository<CustomersOrders,Integer>{
	//自訂Repository 回應值介面必須配合犯行
	@Query(value="Select c.CustomerID,CompanyName,Address,Phone,OrderID,OrderDate,RequiredDate,ShippedDate From Orders o Inner Join Customers c ON o.CustomerID=c.CustomerID",nativeQuery=true)
	public List<CustomersOrders> ordersAll();
	
	@Query(value="Select c.CustomerID,CompanyName,Address,Phone,OrderID,OrderDate,RequiredDate,ShippedDate From Orders o Inner Join Customers c ON o.CustomerID=c.CustomerID Where OrderID=:oid",nativeQuery=true)
	public CustomersOrders ordersIdQuery(@Param("oid")int orderid);

}
