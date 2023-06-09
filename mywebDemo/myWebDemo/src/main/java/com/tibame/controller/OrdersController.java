package com.tibame.controller;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//訂單作業控制器
@Controller
public class OrdersController {
	//訂單查詢作業-依照訂單編號
	@RequestMapping(path="/orders/qry",
			method= {RequestMethod.GET,RequestMethod.POST})
	public String ordersQry(String orderId,Model model) {
		if(orderId!=null) {
			//TODO 進行查詢作業
			System.out.println("輸入的訂單編號:"+orderId);
			model.addAttribute("orderId",orderId);
		}
		//進行狀態資料模組持續
		model.addAttribute("message","請輸入訂單編號");
		return "ordersqry";
	}

}
