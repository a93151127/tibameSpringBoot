package com.tibame.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tibame.entity.Customers;



//測試@Resource
@Controller
public class ResourceController {
	//Attribute
	@Resource(name="cust",description="by Name優先考量")
	private Customers customers;
	
	@Resource
	private Customers customers2;
	
	
	
	@GetMapping(path="/customers/add")
	public String customersAdd(Model model) {
		//參照注入的客戶物件
		model.addAttribute("customers",customers);
		return "customersadd";
	}
	
	@GetMapping(path="/customers/add2")
	public String customersAdd2(Model model) {
		//參照注入的客戶物件
		model.addAttribute("customers",customers2);
		return "customersadd";
	}
	


}
