package com.tibame.controller;

import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.tibame.domain.Company;
import com.tibame.domain.Employees;


//@Inject注入應用
@Controller
public class InjectController {
	//Attribute
	//使用參數注入
	@GetMapping(path="/employees/inject/add")
	@Inject
	public String customersAdd(Employees employee) {
		//隱含參考注入的Customers物件到View Page
		
		return "employeesadd";
	}

}
