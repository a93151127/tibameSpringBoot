package com.tibame.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tibame.domain.Message;

@RestController
public class HelloService {
	
	//建構子
	public HelloService() {
		System.out.println("Hello Service產生個體物件...");
	}
	//回應一個Text/plain 簡單文字串到前端
	@GetMapping(path="/api/hello/helloworld",produces="text/plain")
	public String helloWorld() {
		return "世界和平";
	}
	//回應物件序列化成Json內容
	@GetMapping(path="/api/hello/hellowobject",produces="application/json")
	@ResponseBody
	public Message helloWorldObject() {
		//建構JavaBean物件
		Message message=new Message();
		message.setCode(200);
		message.setMsg("Hello World");
		return message;
	}
	

}
