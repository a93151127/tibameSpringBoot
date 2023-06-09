package com.tibame.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.tibame.component.CustomersRepository;
import com.tibame.domain.Customers;
import com.tibame.domain.DataUtility;

//客戶資料維護控制器
@Controller
@RequestMapping(path="/customers")
public class CustomersController {
	//依照型別注入依賴物件DataSource
	@Autowired
	private SQLServerDataSource datasource;
	//注入Jpa Repository
	@Autowired
	private CustomersRepository customerRep;
	//調用查詢頁面(依照客戶編號)
	@RequestMapping(path= {"/query/byid"},method= {RequestMethod.GET})
	public String customersQryById() {
		
		return "custqryid"; //頁面名稱
	}
	
	//採用QueryString傳遞參數
	@RequestMapping(path= {"/query/id"},method= {RequestMethod.GET})
	public String customersQryParameter(String cid)
	{
		System.out.println("查詢的客戶編號:"+cid);
		return "custqryid"; //頁面名稱
	}
	
	//採用QueryString傳遞參數(明確前端URL參數名稱)
	@RequestMapping(path= {"/query/id2"},method= {RequestMethod.GET})
	public String customersQryParameter2(@RequestParam(name="custid") String cid)
	{
		System.out.println("查詢的客戶編號:"+cid);
		return "custqryid"; //頁面名稱
	}
	
	//採用QueryString傳遞參數(明確前端URL參數名稱明確格式為整數)
	@RequestMapping(path= {"/query/id3"},method= {RequestMethod.GET})
	public String customersQryParameter3(@RequestParam(name="custid") int cid)
	{
		System.out.println("查詢的客戶編號:"+cid);
		return "custqryid"; //頁面名稱
	}
	
	//採用QueryString傳遞參數(參數可有可無)
	@RequestMapping(path= {"/query/id4"},method= {RequestMethod.GET})
	public String customersQryParameter4(@RequestParam(name="custid",
	required=false) String cid)
	{
		System.out.println("查詢的客戶編號:"+cid);
		return "custqryid"; //頁面名稱
	}
	
	//採用QueryString傳遞參數(參數具有預設值)
	@RequestMapping(path= {"/query/id5"},method= {RequestMethod.GET})
	public String customersQryParameter5(@RequestParam(name="custid",
	defaultValue="ABCDE") String cid)
	{
		System.out.println("查詢的客戶編號:"+cid);
		return "custqryid"; //頁面名稱
	}
	
	//依照城市找客戶資料
	@RequestMapping(path= {"/query/city"},
			method={RequestMethod.GET,RequestMethod.POST})
	public String customersQryByCity(String city,HttpServletRequest request
			,Model model) {
		//呼喚類別成員要資料模組(城市)
		String[] cities=DataUtility.getCitites();
		//進行資料模組持續狀態
		model.addAttribute("cities",cities);
		//判斷傳送方法
		if(request.getMethod().equals("POST")) {
			//作業
		}else {
			//第一次請求
		}
		return "customerscity";
	}
	
	//------------------error錯誤訊息如何呈現---------------
	//客戶新增作業表單頁面
	@RequestMapping(path="/customers/addform",
			method= {RequestMethod.GET,RequestMethod.POST})
	public String customersAddForm(Customers customers,
			HttpServletRequest request,Model model,Errors errors) {
		System.out.println("錯誤物件:"+errors);
		//判斷傳送方式
		if(request.getMethod().equals("POST")) {
			//進行輸入驗證
			if(customers.getCustomerId()==null || customers.getCustomerId().length()==0)
			{
				errors.rejectValue("customerId", "客戶編號必須要輸入!!!");
			}
			if(customers.getCompanyName()==null || customers.getCompanyName().length()==0) {
				errors.rejectValue("companyName", "公司行號必須要輸入!!!");
			}
			System.out.println(errors);
		}else
		{
			//第一次請求
			
			
		}
		//持續錯誤狀態
		boolean isError=false;
		if(errors.hasErrors()) {
			isError=true;
			System.out.println(errors.getAllErrors().get(0).getCode());
		}
		
		model.addAttribute("errors",errors);
		model.addAttribute("iserror",isError);
		
		return "customersaddform";
		
	}
	//-----------產生國家別清單 後端渲染畫面應用(thymeleaf)
	@GetMapping(path="/countryqry")
	public String countryQry(Model model) {
		//設定國家別清單字串陣列
		List<String> countries=new ArrayList<>();
		countries.add("USA");
		countries.add("UK");
		countries.add("France");
		countries.add("中華民國");
		model.addAttribute("country",countries);
		return "countryqry";
	}
	
	//調用具有國家別清單的客戶查詢頁面
	@GetMapping(path="/listqry")
	public String countryByList(Model model) {
		//集合物件
		List<String> countries=new ArrayList<>();
		//參考國家別字串
		countries.add("USA");
		countries.add("UK");
		countries.add("France");
		countries.add("中華民國");
		model.addAttribute("country",countries);//狀態持續到View Page
		return "countrylist";
		
	}
	
	//使用thymeleaf選染下拉式功能表 與進行選項透過表單回應
	@RequestMapping(path="/listparamqry",
			method={RequestMethod.GET,RequestMethod.POST})
	public String countryByParam(@RequestParam(name="country")String country
			,HttpServletRequest request
			,Model model) {
		//判斷是否Postback進行查詢
		if(request.getMethod().equals("POST")) {
		}
		//集合物件
		List<String> countries=new ArrayList<>();
		//參考國家別字串
		countries.add("USA");
		countries.add("UK");
		countries.add("France");
		countries.add("中華民國");
		model.addAttribute("country",countries);//狀態持續到View Page
		
		return "countryparam";
		
	}
	
	//使用注入依賴的DataSource
	@GetMapping(path="/datasource")
	public String dataSourceDemo(Model model) {
		try {
			String dbName=datasource.getConnection().getCatalog();
			model.addAttribute("dbName",dbName);
			model.addAttribute("ds",datasource);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "showdatasource";
	}
	
	//使用自訂的CustomersRepository取出所有客戶資料
	@GetMapping(path="/customers/all")
	public String customersAll(Model model) {
		//使用自訂Jpa Repository
		List<com.tibame.entity.Customers> customers=customerRep.findAll();
		model.addAttribute("customers",customers);
		return "showcustomers";
	}
	
	//使用自訂的CustomersRepository取出所有客戶資料
	@GetMapping(path="/key/{cid}")
	public String customersByCid(@PathVariable(name="cid")String customerId
			,Model model) {
			//使用自訂Jpa Repository
			com.tibame.entity.Customers customers=
					customerRep.findByCustomerId(customerId);
			model.addAttribute("customers",customers);
			return "showsinglecustomers";
		}
	
}



