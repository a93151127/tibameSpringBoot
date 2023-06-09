package com.example.myweb.controller;

import com.example.myweb.entity.MemberEntity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//進行關於會員操作(註冊/查詢/登入驗證/後台的刪除作業)
//佈署為控制器
@Controller
public class MemberController {
	
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
		System.out.println("帳號:"+userName);
		System.out.println("密碼:"+password);
		System.out.println("信箱:"+email);
		return "hello";
	}
	
	//前端傳遞進來的註冊資訊
	//表單欄位form field name=xxxx 對應注入進來的JavaBean 透過屬性Property自動封裝進來
	@PostMapping(path= {"/member/register"})
	public String memberSave2(Model model, MemberEntity entity) {
		System.out.println("帳號:"+entity.getUserName());
		System.out.println("密碼:"+entity.getPassword());
		System.out.println("信箱:"+entity.getEmail());

		return "hello";
	}
	
	
	//查詢特定會員作業(登入驗證/後臺查詢作業) 第一次請求調用查詢表單頁面 與後送查詢作業指向同一個Action
	//參數注入依賴(DI-Dependency Injection)物件 注入Servlet API底層物件
	@RequestMapping(path= {"/member/qry"},method= {RequestMethod.GET,RequestMethod.POST})
	public String memberQry(String username, HttpServletRequest request) {
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
