package com.tibame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tibame.component.CustomersRepository;
import com.tibame.domain.Message;
import com.tibame.entity.Customers;

@RestController
public class CustomersService {
	//Attribute
	//注入JpaRepositiory
	@Autowired
	private CustomersRepository customersRep;
	
	//查詢所有客戶服務
	@GetMapping(path="/api/customers/all/rawdata",produces="application/json")
	public ResponseEntity<Customers> customersAllQry(){
		//透過Jpa進行資料存取
		List<Customers> result=customersRep.findAll();
		//建構ResponseEntity物件
		@SuppressWarnings("rawtypes")
		ResponseEntity response=null;
		if(result.size()>0) {
			response=ResponseEntity.ok(result);
		}else
		{
			//無客戶資料
			//建構訊息物件
			Message message=new Message();
			message.setCode(400);
			message.setMsg("查無任何客戶資料");
			response=ResponseEntity.status(400).body(message);
			
		}
		return response;
		
		
	}
	//採用Path傳遞客製化編號進行資料查詢
	@SuppressWarnings("unchecked")
	@GetMapping(path="/api/customers/key/{cid}/rawdata")
	public ResponseEntity<Customers> customersQryById(
			@PathVariable("cid")String customerid) {
		Customers customers=customersRep.findByCustomerId(customerid);
		@SuppressWarnings("rawtypes")
		ResponseEntity response=null;
		//判斷是否查有資料
		if(customers==null) {
			Message message=new Message();
			message.setCode(400);
			message.setMsg(String.format("查無該客戶:%s 資料",customerid));
			response=ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}else {
			response=ResponseEntity.ok(customers);
		}
		return response;
	}
	
	//採用QueryString傳遞客製化編號進行資料查詢
		@SuppressWarnings("unchecked")
		@GetMapping(path="/api/customers/byid/rawdata")
		public ResponseEntity<Customers> customersQryByParamId(
				@RequestParam("cid")String customerid) {
			Customers customers=customersRep.findByCustomerId(customerid);
			@SuppressWarnings("rawtypes")
			ResponseEntity response=null;
			//判斷是否查有資料
			if(customers==null) {
				Message message=new Message();
				message.setCode(400);
				message.setMsg(String.format("查無該客戶:%s 資料",customerid));
				response=ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(message);
			}else {
				response=ResponseEntity.ok(customers);
			}
			return response;
		}
	

}
