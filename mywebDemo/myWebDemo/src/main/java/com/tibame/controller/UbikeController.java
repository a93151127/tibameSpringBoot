package com.tibame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UbikeController {
	//輸入區域找出腳踏車基座狀態
	@GetMapping(path="/ubike/qry")
	public String ubikeQry() {
		//調用UI
		return "ubikeqry";
	}

}
