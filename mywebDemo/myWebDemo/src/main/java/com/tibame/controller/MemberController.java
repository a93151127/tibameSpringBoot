package com.tibame.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tibame.domain.MemberEntity;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

//進行關於會員操作(註冊/查詢/登入驗證/後台的刪除作業)
//佈署為控制器
@Controller
public class MemberController {
	//Data Field-Attribute注入(DI) 注入生產連接物件的DataSource物件(工廠)
	//採用型別認定(Spring Container裡面有沒有這一個類型的物件)
	@Autowired
	private SQLServerDataSource mydatasource;
	//注入JdbcTemplate(必須反轉注入DataSource) IoC-注入控制反轉
	private JdbcTemplate jdbcTemplate;
	
	//註冊表單頁面調用action
	//提供請求方式GET 設定端點
	@GetMapping(path= {"/member/register"})
	public String memberAdd() {
		//直接調用頁面
		return "register";
	}
	
	//前端傳遞進來的註冊資訊
	//表單欄位form field name=xxxx 對應參數名稱 自動封裝進來
	@PostMapping(path= {"/member/save"})
	public String memberSave(String userName,String password,String email) {
		//TODO會員註冊新增作業
		
		System.out.println("新增資料:"+userName);
		return "ok";
	}
	
	//前端傳遞進來的註冊資訊
	//表單欄位form field name=xxxx 對應注入進來的JavaBean 透過屬性Property自動封裝進來
	@PostMapping(path= {"/member/register"})
	public String memberSave2(Model model,MemberEntity entity) {
		System.out.println(mydatasource);
		System.out.println("新增資料:"+entity.getUserName());
		//要一連接 具有開啟 
		String message=null;
		Connection connection=null;
		try {
			connection=mydatasource.getConnection();
			if(!connection.isClosed()) {
				//連接開啟
				System.out.println("連接開啟成功...");
				//下具有參數架構的SQL Insert語法 ? 採用參數架構
				String sql="Insert Into membership(username,password,email) values(?,?,?)";
				//透過連接物件獲取命令物件
				PreparedStatement pre=connection.prepareStatement(sql);
				//設定參數內容(參數順序從一開始)
				pre.setString(1,entity.getUserName());
				pre.setString(2,entity.getPassword());
				pre.setString(3,entity.getEmail());
				//執行新增作業
				pre.executeUpdate(); //執行新增或者刪除或者修改
				//狀態設定
				message=String.format("%s 客戶新增成功",entity.getUserName());
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//狀態設定
			System.out.println("連接失敗..."+e.getMessage());
			message=String.format("%s 客戶新增失敗",entity.getUserName());
		}finally {
			if(connection!=null) {
				try {
					connection.close(); //收回連接集區 Queue 如果有要 直接配置 否則慢慢關閉
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //成功與失敗均要進行關閉-收集連接集區
			}
		}
		
		//狀態如何持續到頁面去???
		model.addAttribute("message",message);
		//TODO 進行資料新增
		return "ok";
	}
	
	
	//查詢特定會員作業(登入驗證/後臺查詢作業) 第一次請求調用查詢表單頁面 與後送查詢作業指向同一個Action
	//參數注入依賴(DI-Dependency Injection)物件 注入Servlet API底層物件
	@RequestMapping(path= {"/member/qry"},method= {RequestMethod.GET,RequestMethod.POST})
	public String memberQry(String username,HttpServletRequest request) {
		//判斷是否有傳遞key 加上傳送方式Request Method-GET(第一次來/postback進行查詢 傳送會是POST)
		String method=request.getMethod();
		//判斷是否為第一次請求(調用查詢畫面page到前端) else 前端傳送查詢username 進行會員資料查詢
		if(method.equals("GET")) {
			//超連結過來的(第一次請求 調用查詢頁面)
		}else  if(method.equals("POST")){
			//進行會員資料查詢
			System.out.println("查詢使用者名稱:"+username);
			//TODO 查詢作業
		}
		System.out.println("傳送方式:"+method);
		return "query";
		
		
	}
	
	
	
	
	

}
