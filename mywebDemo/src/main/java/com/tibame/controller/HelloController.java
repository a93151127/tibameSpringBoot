package com.tibame.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//用來打招呼項
//採用POJO架構設計
@Controller
@RequestMapping(path= {"/hello"})
public class HelloController {
	//建構子
	public HelloController() {
		System.out.println("Hello控制器產生Instance...");
	}
	//打招呼方法
	@RequestMapping("/helloworld")
	public String helloWorld()
	{
		//處理流程
		return "hello"; //回應調用頁面名稱旗標
	}
	
	//採用參數注入HttpServletRequest物件
	@RequestMapping(path= {"/hello/scope"})
	public String passingHello(HttpServletRequest request) {
		//進行Request範圍狀態管理
		request.setAttribute("hello","世界和平");
		return "whohello";
	}
	

}
