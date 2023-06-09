package com.tibame.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tibame.entity.Company;
import com.tibame.entity.Employees;

//公司與員工互動關係Service
@RestController
public class CompanyService {
	
	//公司與多個員工物件查詢
	@GetMapping(path="/api/company",produces="application/json")
	@ResponseBody
	public Company getCompany() {
		return companyData();
	}
	
	//關聯資料服務
	public static Company companyData()
	{
		//建構公司物件
		Company com=new Company();
		com.setCompanyName("凱旋企業");
		com.setAddress("台北市和平東路");
		com.setPhone("02-12345678");
		//建構員工物件
		Employees emp1=new Employees();
		emp1.setName("張無忌");
		Employees emp2=new Employees();
		emp2.setName("張三封");
		com.getEmployees().add(emp1);
		com.getEmployees().add(emp2);
		return com;
		
	}
	
	
	

}
