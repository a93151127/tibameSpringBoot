package com.tibame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tibame.component.Car;
import com.tibame.domain.Engine;

@Controller
public class CarController {
	//Data Field注入依賴物件
	@Autowired
	private Car car;
	
	@RequestMapping(path="/car/show",method= {RequestMethod.GET})
	public String showCar(Model model) {
		
		//參照注入的Car物件(持續狀態到View)
		model.addAttribute("car",car);
		return "carshow";
	}

}
