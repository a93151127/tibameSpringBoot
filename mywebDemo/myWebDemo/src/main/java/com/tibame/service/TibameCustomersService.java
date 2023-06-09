package com.tibame.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.khh.entity.Customer;
import com.khh.entity.TibameCustomersRepository;
import com.tibame.entity.Customers;
import com.khh.entity.Message;

//提供客戶資料維護的資訊服務REST API
//使用JEE JAX-RS API規範
@RestController
public class TibameCustomersService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//注入JPA 自訂介面Repository
	@Autowired
	private TibameCustomersRepository customersRep;
	
	//檢視注入的JdbcTemplate注入
	@GetMapping(path="/api/jdbctemplate")
	public String jdbcTemplateInject() {
		String dbName=null;;
		try {
			dbName = jdbcTemplate.getDataSource()
					.getConnection().getCatalog();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbName;
	}
	//新增客戶資料 JPA應用
	//接受傳遞進來的客戶資料 進行資料更新
		//要求前端採用Request method-PUT 同時要求Request Header Content-Type:application/json
		@PostMapping(path="/customers/add/rawdata",
				produces="application/json;charset=UTF-8",
				consumes="application/json")
		public Message customersInsert(@RequestBody com.khh.entity.Customer 
				customer) 
		{
			Message msg=new Message();
			//判斷是否已經存在
			if(!customersRep.existsById(customer.getCustomerId())) {
				this.customersRep.save(customer);
				msg.setCode(200);
				msg.setMsg("客戶新增成功");
			}else {
				msg.setCode(200);
				msg.setMsg("客戶編號已經存在!新增失敗!!");
			}
			return msg;
		
		}
	
	//使用JdbcTemplate 查詢客戶資料
	@GetMapping(path="/api/customers/all",produces="application/json")
	public List<Customers> customersQuery() {
		String sql="Select CustomerID,CompanyName,"
				+ "Address,Phone,Country From Customers";
		BeanPropertyRowMapper<Customers> mapper=
				BeanPropertyRowMapper.newInstance(Customers.class);
		List<Customers> result=jdbcTemplate.query(sql,mapper);
		return result;
	}
	
	//接受傳遞進來的客戶資料 進行資料更新
	//要求前端採用Request method-PUT 同時要求Request Header Content-Type:application/json
	@PutMapping(path="/customers/update/rawdata",
			produces="application/json;charset=UTF-8",
			consumes="application/json")
	public Message customersUpdate(@RequestBody Customer customer,
			HttpServletResponse response) {
		String update="Update Customers set CompanyName=?,Address=?,"
				+ "Phone=?,Country=? Where CustomerID=?";
		Message msg=new Message();
		//借助注入進來的JdbcTemplate
		try {
		int affect=jdbcTemplate.update(update,
				customer.getCompanyName(),
				customer.getAddress(),
				customer.getPhone(),
				customer.getCountry(),
				customer.getCustomerId()
				);
		//客戶被刪除了 還是更新成功 零筆
		if(affect>0) {
			msg.setCode(200);
			msg.setMsg("客戶資料更新成功");
			//預設Http status code 200
		}else {
			//Http status code-400
			response.setStatus(400);
			msg.setCode(400);
			msg.setMsg("客戶資料更新不到 資料不存在");
		}
			
		}
		catch(DataAccessException ex) {
			response.setStatus(400);
			//處理 回Http狀態碼 同時回應訊息
			msg.setCode(400);
			msg.setMsg("客戶資料更新失敗 請檢查");
		}
		
		return msg;
	}

	//刪除相對客戶資料服務 採用Path 參數架構
	//回應一個Http Response 含有特製Entity(Http Body)
	@DeleteMapping(path= {"/customers/delete/key/{cid}/rawdata"},
			produces="application/json")
	public ResponseEntity<Message> customersDelete(
			@PathVariable("cid")String customerId) {
		//先找出有這一個資料(有一個對應的Entrity物件)
		Customer customer=this.customersRep.findByCustomerId(customerId);
		ResponseEntity<Message> httpEntity=null;
		Message msg=new Message();
		if(customer!=null) {
			try {
				//找到了 進行該物件的刪除
				this.customersRep.delete(customer); //可能產生例外(資料表之間關連進行Constarints)
				msg.setCode(200);
				msg.setMsg(customerId+" 客戶資料刪除成功");
				httpEntity=new ResponseEntity<>(msg,HttpStatus.OK); //200
			}catch(Exception ex) {
				msg.setCode(400);
				msg.setMsg(customerId+" 客戶資料有訂單 無法刪除!!");
				httpEntity=new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST); //400
			}
		}else {
			//查無該客戶資料
			msg.setCode(400);
			msg.setMsg(customerId+" 客戶資料不存在");
			
			httpEntity=new ResponseEntity(msg,HttpStatus.BAD_REQUEST);
		}
		return httpEntity;
	}
	
	
}

