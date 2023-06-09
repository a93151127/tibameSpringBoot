package com.tibame.controller;

import java.io.IOException;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.khh.entity.Customer;
import com.khh.entity.CustomersCRUDRepository;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
//POJO(Plain Old Java Object)
//客戶CRUD控制器
//注入一個依賴關係(DI-Dependency Injection)的JavaBean 進行客戶資料新增用
//註冊特定的properties file
@Controller
@PropertySource(value= {"classpath:services.properties"})
@RequestMapping(path="/tibamecustomers")
public class TibameCustomersController {
	//注入客戶物件Customer Attribute注入
	//@Autowired
	//private Customer customer;
	//注入資源檔項目
	@Value("${service.customers.update}") //使用Spring EL
	private String customersUpdateService;
	//刪除服務位址
	@Value("${service.customers.delete}") //使用Spring EL
	private String customersDeleteService;
	
	//自動縫合 注入物件(使用型別認定-Spring Container)
	@Autowired
	private SQLServerDataSource mySqlDataSource;
	//Attribute注入JdbcTemplate
	@Autowired
	private JdbcTemplate jdbcTemplate; //實現DAO(Data Access Object)
	//注入JPA
	@Autowired
	private CustomersCRUDRepository curdRepository;

	//新增客戶表單頁面入口
	//注入(DI Dependency Injection 依賴注入)Model 背後封裝前端帶進來的資訊 Request物件
	//描述端點
	@RequestMapping(path="/add")
	public String customersAdd(Model model) {
		//產生狀態 一個訊息字串物件
		String message="* 必須輸入客戶資料必要欄位";
		//讓model參照這一個訊息物件
		model.addAttribute("msg",message);
		return "customersform";
	}//message區域變數活到這裡
	
	//客戶新增作業action
	//採用參數注入一個Customer Bean物件 再將表單欄位名稱自動封裝到物件的屬性去
	//採用參數注入SQLServerDataSource物件
	@RequestMapping(path="/save",method= {RequestMethod.POST,RequestMethod.GET})
	public String customersSave(Customer customer,Model model) {
		String dbName;
		System.out.println("DataSource物件:"+mySqlDataSource.toString());
		//有一個封裝所有CRUD(新增修改刪除與查詢元件 Spring JdbcTemplate(DAO設計模式)
		JdbcTemplate dao=new JdbcTemplate();
		dao.setDataSource(mySqlDataSource);
		//新增作業 客戶資料
		String sql="Insert Into Customers(CustomerID,CompanyName,Address,Phone,Country) values(?,?,?,?,?)";
		//進行例外管理
		String msg=null;
		Boolean isSuccess=false;
		try {
			int affect=dao.update(sql,customer.getCustomerId(),customer.getCompanyName(),customer.getAddress(),customer.getPhone(),customer.getCountry());
			//新增成功
			msg=String.format("客戶:%s 新增成功",customer.getCustomerId());
			isSuccess=true;
		}catch(DataAccessException ex) {
			//新增失敗
			msg=String.format("客戶:%s 新增失敗",customer.getCustomerId());
		}
		
		//JdbTemplate必須與DataSource互動
//		try {
//			Connection connection=sqlDataSource.getConnection();
//			
//			if(!connection.isClosed()) {
//				System.out.println("連接上資料庫了...");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//		}
		System.out.println(mySqlDataSource.getURL());
		//狀態要持續到View(ok.html)去
		model.addAttribute("message", msg);
		model.addAttribute("status",isSuccess);
		//加一個狀態 被維護的資料customer
		model.addAttribute("customer",customer);
		//儲存作業
		return "tibameok";
	}
	
	
	//依照國家別進行相關客戶查詢 
	//注入底層封裝前端所有資訊的HttpServletRequest(DI-Dependency Injection-依賴注入)
	@RequestMapping(path= {"/qry/country"},
			method= {RequestMethod.GET,RequestMethod.POST})
	public String customersQry(String country,
			HttpServletRequest request,Model model) {
		//如何認定是第一次來 進行畫面調用 還是postback進行客戶查詢作業
		if(request.getMethod().equals("GET")) {
			//第一次來 調用畫面
			System.out.println("第一次來");
		}else if(request.getMethod().equals("POST")) {
			//進行查詢 使用JdbcTemplate(配合DataSource物件)採用IoC注入控制反轉 直接反轉注入DataSource
			try {
				System.out.println(jdbcTemplate.getDataSource().getConnection().getCatalog());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//採用注入的JdbcTemplate 進行查詢
			String message=null;
			
			//採用參數架構查詢
			String sql="Select CustomerID,CompanyName,Address,Phone,Country "
					+ "From Customers Where Country=?";
			
			List<Customer> customers=jdbcTemplate.query(sql,
					BeanPropertyRowMapper.newInstance(Customer.class),
					new Object[] {country});
			
			//將集合物件序列化Json String(借助third party-google Gson API)
			Gson gson=new Gson();
			String customersJson=gson.toJson(customers);
			
			System.out.println("查詢作業..查詢紀錄數:"+customers.size());
			//狀態要到View Page去
			model.addAttribute("message",message);
			//持續查詢的國家別
			model.addAttribute("country",country);
			model.addAttribute("data",customersJson); //國家別查詢結果序列化String
			//持續狀態到View Page去
			model.addAttribute("updateService",this.customersUpdateService);
			model.addAttribute("deleteService",this.customersDeleteService);
			//查詢序列化資訊 
			
		}
		return "customersqry";
		//return "customersqrysource";
	}
	
	//TODO客戶資料新增作業
	@RequestMapping(path="/insert",method= {RequestMethod.GET,RequestMethod.POST})
	public String customersInsert(Customer customer,Model model,HttpServletRequest request) {
		//判斷是否為Postback進行新增
		String message="";
		if(request.getMethod().equals("POST")) {
			//新增作業
			System.out.println(customer.getCompanyName());
			//使用JPA進行新增作業
			//save 針對Entity如果後端資料表不存在 採用Insrt Into....
			//後端資料表已經存在這一個記錄 採用update...更新記錄
			try {
				int r=curdRepository.insert(customer);
				System.out.println(r);
				message=String.format("客戶:%s 新增成功",customer.getCustomerId());
			}catch(Exception ex) {
				message="新增失敗 請檢查輸入的資料，可能是編號重複了";
			}
			
		}
		//傳遞Model 配合頁面表單欄位維護
		model.addAttribute("message",message);
		model.addAttribute("customer",customer);		
		return "customersinsert";
	}
	
	//----採用QueryString 參數架構----
	//刪除輸入國家別的客戶資料作業
	@RequestMapping(path="/country/delete",
			method= {RequestMethod.GET,RequestMethod.POST})
	public String customersDeleteByCountry(String country,Model model) {
		//產生國家別清單 選染下拉式畫面
		List<Map<String,Object>> result=this.jdbcTemplate.queryForList("Select distinct Country from Customers where not Country is null");
		Object[] items=result.toArray();
		System.out.println(items);
		List<String> countries=new ArrayList<>();
//		for(Map<String,Object> r:items) {
//			countries.add(r.get("country").toString());
//		}
//		System.out.println(countries);
		model.addAttribute("country",countries);
		return "";
	}
	
	//使用國家別進行刪除作業(使用ORM JPA)
	@RequestMapping(path="/delete/bycountry/",method= {RequestMethod.GET,RequestMethod.POST})
	public String deleteForCountry(String country,HttpServletRequest request
			,HttpServletResponse response,Model model) {
		//判斷採用哪一種請求方法(決定是進行刪除作業 還是調用表單頁面)
		if(request.getMethod().equals("GET")) {
			//發出一個許可證(採用Response Http Header)
			response.addHeader("cred","1234567890"); //埋入一個正常請求的Token 不代表請求header對應
			//借助頁面隱藏性標籤 帶這一個Token
			model.addAttribute("token","1234567890");
			return "showdelete"; //直接調用頁面
		}else //POST
		{
			//看看是否合法來源?取隱藏性標籤Token
			String cred=request.getParameter("cred");
			if(cred==null || cred.length()==0) {
				//非法來的
				try {
					response.sendError(403, "非法管道過來的");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else //POST Pesistence token
			{
				//發出一個許可證(採用Response Http Header)
				model.addAttribute("token","123456789");
				String message=null;
				//使用注入 Jpa Repository
				try {
					int affect=curdRepository.deleteForCountry(country);
					//判斷刪除記錄數
					if(affect>0) {
						//刪除若干紀錄
						message=String.format("%s 國家別刪除記錄數:%d 已經完成",country,affect);
						
					}else {
						System.out.println("刪除失敗");
						message=String.format("國家別:%s 刪除失敗",country);
					}
				}catch(Exception ex) {
					message=String.format("國家別:%s 刪除失敗，可能在訂單關聯上",country);
				}
				//進入狀態管理
				model.addAttribute("message",message);
			}
			return "showdelete";
		}
		
		
		
		
	}
	

}

