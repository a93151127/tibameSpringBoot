package com.tibame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tibame.domain.Employees;

//員工資料處理控制項
@Controller
public class EmployeesController {
	@Autowired 
	private Employees emp;
	
	//產生一個員工物件進行新增頁面渲染
	@RequestMapping(path="/emp/add",method= {RequestMethod.GET})
	public String employeesForm() {
		System.out.println(emp.getCompany().getCompanyName());
		return "empform";
	}
}
